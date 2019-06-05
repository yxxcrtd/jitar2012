# coding=utf-8
from cn.edustar.jitar.pojos import UserType
from base_manage import BaseManage
from user_query import UserQuery
from util import Util
from base_action import *

class admin_usertype_manage(BaseManage, Util):
    user_type_list = "/WEB-INF/ftl/admin/admin_usertype_manage.ftl"
    def __init__(self):
        BaseManage.__init__(self)
        self.userService = __jitar__.userService
    def execute(self):
        if self.isSystemUserAdmin() == False:
            self.addActionError(u"您不具有用户管理权限。")
            return self.ERROR
        cmd = self.params.getStringParam("cmd")
        if cmd == None or cmd == "":
            cmd = "list"
        if cmd == "edit":
            typeId = self.params.safeGetIntParam("typeId")
            userType = self.userService.getUserTypeById(typeId)
            if userType == None:
                self.addActionError(u"不能加载用户类型对象。")
                return self.ERROR
            request.setAttribute("userType",userType)
            return "/WEB-INF/ftl/admin/admin_usertype_edit.ftl"
        elif cmd == "delete":
            typeId = self.params.safeGetIntParam("typeId")
            userType = self.userService.getUserTypeById(typeId)
            if userType == None:
                self.addActionError(u"不能加载用户类型对象。")
                return self.ERROR
            if userType.isSystem:
                self.addActionError(u"不允许删除系统用户类型定义。")
                return self.ERROR
            # 删除用户表的引用
            qry = UserQuery("u.userId, u.userType")
            qry.orderType = None
            qry.userTypeId = typeId
            user_list = qry.query_map(qry.count())
            if user_list != None and len(user_list) > 0:
                for user in user_list:
                    userTypeId = user["userType"]
                    userTypeId = userTypeId.replace("/" + str(typeId) + "/","/")
                    if userTypeId == "/":
                        userTypeId = None
                    self.userService.updateUserType(int(user["userId"]), userTypeId)
            self.userService.deleteUserType(userType)
            
        if request.getMethod() == "POST":            
            if cmd == "add":
                return self.add_user_type()
            
        user_type_list = self.userService.getAllUserType()
        request.setAttribute("user_type_list",user_type_list)
        return self.user_type_list
    
    def add_user_type(self):
        typeName = self.params.safeGetStringParam("typeName")
        if typeName == "":
            self.addActionError(u"请输入名称。")
            return self.ERROR
        #判断是否存在
        user_type_list = self.userService.getAllUserType()
        if user_type_list != None and len(user_type_list) > 0:
            for u in user_type_list:
                if u.typeName == typeName:
                    self.addActionError(u"该名称已经存在，请重新输入。")
                    return self.ERROR
        typeId = self.params.safeGetIntParam("typeId")
        if typeId == 0:
            userType = UserType()
        else:
            userType = self.userService.getUserTypeById(typeId)
            if userType == None:
                self.addActionError(u"无法加载用户类型对象。")
                return self.ERROR
        userType.setTypeName(typeName)
        self.userService.saveOrUpdateUserType(userType)
        if typeId == 0:
            self.addActionMessage(u"添加成功。")
        else:
            self.addActionMessage(u"修改成功。")
        self.addActionLink(u"返回列表", "admin_usertype_manage.py")
        return self.SUCCESS       