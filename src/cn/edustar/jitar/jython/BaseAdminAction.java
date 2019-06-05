package cn.edustar.jitar.jython;


/**
 * 提供给 admin_xxx.py 脚本做为基类, 或者内部使用的辅助类.
 * 内部访问 application, request, response 都是使用当前线程中的.
 *
 * @deprecated - 直接使用 JythonBaseAction 即可.
 */
public class BaseAdminAction extends JythonBaseAction {
}
