package com.zp.fw.utils;

import com.zp.fw.bean.ContentItem;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhoupeng
 * @Date 2020-05-12 11:12
 */
public class EpubUtil {

    public static void analyze(String path) throws IOException {
        // epub格式 开始解析
        EpubReader epubReader = new EpubReader();

        Book book = epubReader.readEpubLazy(path, "utf-8");
        // 获得目录的树状结构
        List<ContentItem> items = parseMenu(book);
        for (ContentItem item : items) {
            printChapter(book, item.getUrl());

        }
    }

    public static List<ContentItem> parseMenu(Book book) {

        List<TOCReference> refs = book.getTableOfContents().getTocReferences();
        if (ObjectUtils.isEmpty(refs)) {
            return new ArrayList<>();
        }

        // 打印目录
        List<ContentItem> list = getMenu(refs);
        return list;
    }

    /**
     * Title: getMenu
     * Description: 递归获得目录（树状结构）
     *
     * @param refs 最高等级的目录列表
     * @return
     */
    public static List<ContentItem> getMenu(List<TOCReference> refs) {

        // 表示没有目录可以解析
        if (ObjectUtils.isEmpty(refs)) {
            return null;
        }
        List<ContentItem> list = new ArrayList<>();
        // 获得目录
        for (TOCReference ref : refs) {
            if (!ObjectUtils.isEmpty(ref.getResource())) {
                // 获得章节目录
                ContentItem item = new ContentItem(ref.getTitle(), ref.getCompleteHref(), (int) ref.getResource().getSize());

                if (!ObjectUtils.isEmpty(ref.getChildren())) {
                    // 存在子目录，解析子目录
                    item.getChildren().addAll(getMenu(ref.getChildren()));
                }
                // 将解析好的目录放入
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Title: getChapter
     * Description: 获取单个目录的章节信息
     *
     * @param book 书籍
     * @param href
     */
    public static void printChapter(Book book, String href) {

        // 通过herf获取章节信息
        Resource byHref = book.getResources().getByHref(href);
        String str = "";
        // 章节内容
        String content = "";
        try {
            byte[] resdata = byHref.getData();
            // 拿到章节内容的html文
            str = new String(resdata, byHref.getInputEncoding());
            // 解析html文
            Document doc = Jsoup.parse(str);
            // 获得所有的div
            Elements plates = doc.select("p");
            // 循环获取p的文字，并拼接成最后的数据
            for (Element plate : plates) {
                if (StringUtils.isEmpty(plate.text())) {
                    // 如果p为空，直接循环下一个即可
                    continue;
                }
                if (StringUtils.isEmpty(content)) {
                    content = "    " + plate.text();
                } else {
                    content = content + "\n    " + plate.text();
                }
            }

            System.out.println(content);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
