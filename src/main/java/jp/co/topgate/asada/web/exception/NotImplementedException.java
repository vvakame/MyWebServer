package jp.co.topgate.asada.web.exception;

/**
 * Created by yusukenakashima0701 on 2017/04/22.
 */
public class NotImplementedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "このサーバーで実装されていないHTTPメソッドでリクエストメッセージがきました";
    }
}