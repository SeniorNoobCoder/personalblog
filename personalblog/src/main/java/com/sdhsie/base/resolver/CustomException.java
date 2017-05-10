package com.sdhsie.base.resolver;

/**
 * 
  * @ClassName: CustomException
  * @Description: 自定义异常类型
  * @author Administrator
  * @date 2016-3-15 上午09:58:26
  *
 */
@SuppressWarnings("serial")
public class CustomException extends Exception {
    
    private String message;
    
    public CustomException(){}
    
    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    

}