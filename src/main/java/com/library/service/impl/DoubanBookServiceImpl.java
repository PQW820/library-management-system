package com.library.service.impl;

import com.library.entity.Book;
import com.library.service.DoubanBookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 图书API服务实现类（使用 Open Library API）
 */
@Service
public class DoubanBookServiceImpl implements DoubanBookService {
    
    private static final Logger logger = LoggerFactory.getLogger(DoubanBookServiceImpl.class);
    private static final String OPEN_LIBRARY_API_URL = "https://openlibrary.org/api/books?bibkeys=ISBN:";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public Book searchBookByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            logger.warn("Open Library API: ISBN is null or empty");
            return null;
        }
        
        String cleanIsbn = isbn.trim().replaceAll("-", "");
        logger.info("Open Library API: Starting search for ISBN: {}, cleaned ISBN: {}", isbn, cleanIsbn);
        
        try {
            URL url = new URL(OPEN_LIBRARY_API_URL + cleanIsbn + "&jscmd=data&format=json");
            logger.debug("Open Library API: Request URL: {}", url.toString());
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(20000);
            
            int responseCode = connection.getResponseCode();
            logger.info("Open Library API: Response code: {}", responseCode);
            
            if (responseCode != 200) {
                String errorMessage = readErrorStream(connection);
                logger.error("Open Library API: Request failed with status code: {}, error message: {}", responseCode, errorMessage);
                return null;
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            String responseBody = response.toString();
            logger.debug("Open Library API: Response body length: {} characters", responseBody.length());
            
            if (responseBody == null || responseBody.isEmpty()) {
                logger.warn("Open Library API: Response body is empty for ISBN: {}", cleanIsbn);
                return null;
            }
            
            Book book = parseOpenLibraryResponse(responseBody, cleanIsbn);
            if (book == null) {
                logger.warn("Open Library API: Failed to parse response for ISBN: {}, response: {}", cleanIsbn, responseBody.length() > 500 ? responseBody.substring(0, 500) + "..." : responseBody);
            } else {
                logger.info("Open Library API: Successfully retrieved book info for ISBN: {}, title: {}", cleanIsbn, book.getTitle());
            }
            
            return book;
            
        } catch (java.net.ConnectException e) {
            logger.error("Open Library API: Connection failed for ISBN: {}, error: {}", cleanIsbn, e.getMessage());
            return null;
        } catch (java.net.SocketTimeoutException e) {
            logger.error("Open Library API: Timeout for ISBN: {}, error: {}", cleanIsbn, e.getMessage());
            return null;
        } catch (java.io.IOException e) {
            logger.error("Open Library API: IO exception for ISBN: {}, error: {}", cleanIsbn, e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Open Library API: Unexpected exception for ISBN: {}, error: {}", cleanIsbn, e.getMessage());
            return null;
        }
    }
    
    private String readErrorStream(HttpURLConnection connection) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                errorResponse.append(line);
            }
            reader.close();
            return errorResponse.toString();
        } catch (Exception e) {
            logger.warn("Failed to read error stream: {}", e.getMessage());
            return "Unknown error";
        }
    }
    
    private Book parseOpenLibraryResponse(String jsonResponse, String isbn) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            String isbnKey = "ISBN:" + isbn;
            if (!root.has(isbnKey)) {
                logger.warn("Open Library API: Response does not contain ISBN:{} data", isbn);
                return null;
            }
            
            JsonNode bookNode = root.get(isbnKey);
            
            if (!bookNode.has("title") || bookNode.get("title").asText().isEmpty()) {
                logger.warn("Open Library API: Response does not contain title field");
                return null;
            }
            
            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(bookNode.has("title") ? bookNode.get("title").asText() : "");
            
            if (bookNode.has("authors") && bookNode.get("authors").isArray() && bookNode.get("authors").size() > 0) {
                JsonNode firstAuthor = bookNode.get("authors").get(0);
                book.setAuthor(firstAuthor.has("name") ? firstAuthor.get("name").asText() : "");
            }
            
            if (bookNode.has("publishers") && bookNode.get("publishers").isArray() && bookNode.get("publishers").size() > 0) {
                JsonNode firstPublisher = bookNode.get("publishers").get(0);
                book.setPress(firstPublisher.has("name") ? firstPublisher.get("name").asText() : "");
            }
            
            if (bookNode.has("publish_date")) {
                String pubdate = bookNode.get("publish_date").asText();
                if (pubdate != null && !pubdate.isEmpty()) {
                    book.setPublishDate(pubdate);
                }
            }
            
            book.setDescription(bookNode.has("description") ? bookNode.get("description").asText() : "");
            
            book.setTotalNumber(1);
            book.setAvailableNumber(1);
            
            return book;
            
        } catch (com.fasterxml.jackson.core.JsonParseException e) {
            logger.error("Open Library API: JSON parse error, response: {}, error: {}", jsonResponse.length() > 200 ? jsonResponse.substring(0, 200) + "..." : jsonResponse, e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Open Library API: Parse exception, error: {}", e.getMessage());
            return null;
        }
    }
}
