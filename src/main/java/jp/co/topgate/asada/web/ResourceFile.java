package jp.co.topgate.asada.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * コンテンツタイプの管理を行うクラス
 *
 * @author asada
 */
public class ResourceFile extends File {
    /**
     * ファイル名のドット
     */
    private static final String FILE_NAME_DOT = "\\.";

    /**
     * ファイル拡張子とコンテンツタイプのハッシュマップ
     */
    private static Map<String, String> fileType = new HashMap<>();

    /**
     * 指定されたファイルの拡張子
     */
    private String extension;

    /**
     * コンストラクタ
     * ファイル拡張子とコンテンツタイプのハッシュマップの初期設定を行う
     *
     * @param filePath リクエストメッセージで指定されたファイルのパス
     */
    public ResourceFile(String filePath) {
        super(filePath);

        String[] s = this.getName().split(FILE_NAME_DOT);
        extension = s[1];

        fileType.put("htm", "text/html");
        fileType.put("html", "text/html");
        fileType.put("css", "text/css");
        fileType.put("js", "text/javascript");

        fileType.put("txt", "text/plain");

        fileType.put("jpg", "image/jpg");
        fileType.put("jpeg", "image/jpeg");
        fileType.put("png", "image/png");
        fileType.put("gif", "image/gif");
    }

    /**
     * コンテンツタイプを取得できるメソッド
     *
     * @return コンテンツタイプを返す
     */
    public String getContentType() {
        return fileType.getOrDefault(extension, "application/octet-stream");
    }
}
