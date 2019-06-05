package cn.edustar.jitar.action;

public class NoAction extends BaseAction {

    /**
     * 空白的Action。
     */
    private static final long serialVersionUID = -3278255290988074374L;
    @Override
    public final String execute() throws Exception{
        return ERROR;
    }
}
