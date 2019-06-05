

function taskclass()
{
  this.tasks=new Array();
  this.task_ptr = 0;
  this.run = null;
}

taskclass.prototype.clearTask=function()
{
  this.tasks = new Array();
}

taskclass.prototype.addTask=function(taskobject)
{
  // 添加一个任务，此任务在后续的页面调用中被重复调用
  this.tasks=this.tasks.concat(taskobject);
  
  if( this.tasks.length ==1)
  {
    taskobject.run();
  }
}

taskclass.prototype.dumpTask=function()
{
  for( var i = 0 ; i < this.tasks.length ; i++ )
  {
    alert(this.tasks[i]);
  }
}

taskclass.prototype.nextTask=function()
{

  this.tasks=this.tasks.slice(1);
  if( this.tasks.length > 0)
  {
    var taskobject = this.tasks[0];
    taskobject.run();
  }
}

taskclass.prototype.taskTickCount=function()
{
  if( this.task_ptr >= this.tasks.length )
    return;
  
  document.all.__idStatFrame.src = tasks[task_ptr++];
}
