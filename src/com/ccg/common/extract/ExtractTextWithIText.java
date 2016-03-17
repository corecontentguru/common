package com.ccg.common.extract;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class ExtractTextWithIText {
	private Map<String, Integer> possibleTitle = new HashMap<String, Integer>();
	private String assumedTitle;
	private int pageCount;
	private List<PageInfo> pageInfoList = new ArrayList<PageInfo>();

	public List<SubTitle> exractTitles(InputStream is) throws IOException{
	       PdfReader reader = new PdfReader(is);
	        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
	        //PrintWriter out = new PrintWriter(new FileOutputStream(txt));
	        TextExtractionStrategy strategy;
	        
	        pageCount = reader.getNumberOfPages();
	        
	        StringBuffer sb = new StringBuffer();
	        
	        //for (int i = 1; i <= 4; i++) {
	        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
	            //System.out.println(strategy.getResultantText());
	            String temp = strategy.getResultantText();
	            String temp2 = temp.trim();
	            int p = temp2.indexOf("\n");
	            
	            PageInfo pageInfo = new PageInfo();
	            pageInfo.setPageNumber(i);
	            pageInfo.setNumOfChart(temp2.length());
	            
	            // if not empty page
	            if(p != -1){

	            	String title = temp2.substring(0, p);
	            	// assume first page fist line is title;
	            	if(assumedTitle != null && title.trim().length() > 0){
	            		assumedTitle = title;
	            	}
	            	
	            	// if the pdf file has header, assume the title is in the pdf page header
	            	if(possibleTitle.containsKey(title)){
	            		int number = possibleTitle.get(title);
	            		possibleTitle.put(title, ++number);
	            		System.out.println("=== " + title + ", " + number);
	            	}else{
	            		possibleTitle.put(title, 1);
	            	}
	            }
	            sb.append(temp).append("\n");
	        }
	        
	    String title = findTitle();
		
		return getTitlWithSubTitle(title, sb.toString());
	}
	
	private String findTitle(){
		Set<String> keySet = possibleTitle.keySet();
		String title = "";
		int number = 0;;
		for(String key : keySet){
			if(key.equalsIgnoreCase("null"))
				continue;
			int n = possibleTitle.get(key);
			if(n > number){
				number = n;
				title = key;
				System.out.println("++++ " + key + ", " + n);
			}
		}
		// if pdf page has page header, most of the page header should have same title string
		if(number < pageCount/2){
			title = this.assumedTitle;
		}
		return title;
	}
	
	private List<SubTitle> getTitlWithSubTitle(String title, String content){
		
		Pattern pattern = Pattern.compile("\\r?\\n\\d+\\.(\\d+\\.)*");
		Matcher matcher = pattern.matcher(content);
		List<SubTitle> subTitle = new ArrayList<SubTitle>();
		//subTitle.add(title);
		while (matcher.find()){
			//System.out.println(matcher.group());
			SubTitle sub = new SubTitle();
			int p0 = matcher.start();
			int p1 = content.indexOf("\n", p0+1);
			String temp = content.substring(p0, p1);
			sub.setName(temp.trim());
			sub.setBeginIndex(matcher.start());
			sub.setPreFix(matcher.group());
			
			subTitle.add(sub);
			//System.out.print("Start index: " + matcher.start());
			//System.out.println("  End index: " + matcher.end());
		}		
		return subTitle;
	}
	
	
	public static void main(String[] args) throws Exception{
		
		InputStream is = new FileInputStream(new File("/Users/zchen323/Downloads/HH60Gsimulatorproposal_sample.docx.pdf"));
		ExtractTextWithIText extract = new ExtractTextWithIText();
		
		List<SubTitle> list = extract.exractTitles(is);
		
		
		for(SubTitle subTitle : list){
			System.out.println(subTitle.toString());
		}
		
		
		/*
		^\d+\.\s\S+
		
		*/
		
		String EXAMPLE_TEST = "This is my small example "
			      + "string which I'm going to " + "use for pattern matching.";

		//String test = "a";
		//System.out.print(EXAMPLE_TEST.matches("\\w.*"));
		//System.out.print(test.matches("\\w.*"));
		
		/*
		 *	1. Sub-title
		 *	2. sub-title
		 *	2.1. sub-sub-title
		 *	2.2. sub-sub-title
		 */
		//Pattern pattern = Pattern.compile("\\r?\\n\\d+\\.(\\d+\\.)*");
		//Matcher matcher = pattern.matcher(result);
		//while (matcher.find()){

		//	System.out.println(matcher.group());
		//	System.out.print("Start index: " + matcher.start());
		//	System.out.println("  End index: " + matcher.end());
		//}
		
//		String sub = result.substring(290, 330);
//		char[] cArray = sub.toCharArray();
//		
//		for(int i = 0; i < cArray.length; i++){
//			System.out.println((int)cArray[i]);
//			System.out.println(">>" + cArray[i] + "<<");
//		}
//		
//		System.out.println(sub);
//		
//		System.out.println(result.indexOf("Applicability"));
		
		
		
	}
}
