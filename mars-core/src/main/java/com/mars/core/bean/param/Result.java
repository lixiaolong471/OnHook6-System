package com.mars.core.bean.param;

/**
 * Created by lixl on 2017/2/17.
 */
public class Result {

    public static final String DEFAULT_SUCCESS_MESSAGE = "success!!";

    public static final String DEFAULT_ERROR_MESSAGE = "error!!";


    public static final int MSG_SUCCESS_CODE = 1;

    public static final int MSG_ERROR_CODE = -1;

    public static final int MSG_NOT_LOGIN_CODE = -10;
    
    public static final int MSG_NOT_ACCESS_TOKEN_CODE = -11;
    
    public static final int MSG_PROMPT_CODE=0;

    protected int code;
    protected String message;


    public Result(int code,String message){
        this.code = code;
        this.message = message;
    }

    public Result(){
        this.code = Result.MSG_SUCCESS_CODE;
        this.message = Result.DEFAULT_SUCCESS_MESSAGE;
    }

    public static final Result getOkResult(){
        return new Result(Result.MSG_SUCCESS_CODE,Result.DEFAULT_SUCCESS_MESSAGE);
    }

    public static final Result getErrorResult(){
        return new Result(Result.MSG_ERROR_CODE,Result.DEFAULT_ERROR_MESSAGE);
    }

    public static final Result getNotLoginResult(){
        return new Result(Result.MSG_NOT_LOGIN_CODE,Result.DEFAULT_ERROR_MESSAGE);
    }
    
    public static final Result getNotAccessTokenResult(){
        return new Result(Result.MSG_NOT_ACCESS_TOKEN_CODE,Result.DEFAULT_ERROR_MESSAGE);
    }

    public static final Result getErrorResult(String message){
        return new Result(Result.MSG_ERROR_CODE,message);
    }
    
    public static final Result getPromptResult(String message){
        return new Result(Result.MSG_PROMPT_CODE,message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
