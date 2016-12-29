package com.example.lakshminarayanabr.inclass06;

/**
 * Created by lakshminarayanabr on 9/27/16.
 */


        import android.util.Xml;

        import org.xml.sax.Attributes;
        import org.xml.sax.SAXException;
        import org.xml.sax.helpers.DefaultHandler;

        import java.io.IOException;
        import java.io.InputStream;
        import java.util.ArrayList;

/**
 * Created by rigot on 9/26/2016.
 */

public class NewsItemUtil {

    static public class NewsItemParser extends DefaultHandler{
        static ArrayList<NewsItem> newsList;
        static NewsItem newsItem;
        static StringBuffer buffer;

        static ArrayList<NewsItem> parseItems(InputStream in) throws IOException, SAXException {
            NewsItemParser parser = new NewsItemParser();
            Xml.parse(in, Xml.Encoding.UTF_8, parser);



            return newsList;
        }

        public NewsItemParser() {
            super();
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            newsList = new ArrayList<NewsItem>();
            buffer = new StringBuffer();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("item")){
                newsItem = new NewsItem();
            }else if(localName.equals("content") && qName.equals("media:content")){
                if(attributes.getValue("width").equals(attributes.getValue("height"))){
                    newsItem.setThumbnailImage(attributes.getValue("url"));
                }else if(!attributes.getValue("width").equals(attributes.getValue("height"))){
                    newsItem.setLargeImage(attributes.getValue("url"));
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if(localName.equals("item")){
                if(newsItem.getLargeImage() == null){
                    newsItem.setLargeImage(newsItem.thumbnailImage);
                }
                newsList.add(newsItem);
            }else if(localName.equals("title")){
                if(newsItem!=null){
                    newsItem.setTitle(buffer.toString().trim());
                    buffer.setLength(0);
                }
            }else if(localName.equals("description")){
                if(newsItem != null){
                    newsItem.setDescription(buffer.toString().trim());
                    buffer.setLength(0);
                }
            }else if(localName.equals("link")){
                if(newsItem!=null){
                    newsItem.setLink(buffer.toString().trim());
                    buffer.setLength(0);
                }
            }else if(localName.equals("pubDate")){
                if(newsItem!=null){
                    newsItem.setDate(buffer.toString().trim());
                    buffer.setLength(0);
                }

            }else if(localName.equals("")){

            }

            buffer.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            buffer.append(ch, start, length);
        }



        public ArrayList<NewsItem> getNewsList() {
            return newsList;
        }
    }
}

