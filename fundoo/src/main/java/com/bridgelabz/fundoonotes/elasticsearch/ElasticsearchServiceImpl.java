package com.bridgelabz.fundoonotes.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.notes.dto.NoteDto;
import com.bridgelabz.fundoonotes.notes.model.Notes;
import com.bridgelabz.fundoonotes.user.model.User;
import com.bridgelabz.fundoonotes.util.UserToken;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService{
	@Autowired
	RestHighLevelClient client;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private UserToken userToken;
	
private String INDEX="index";
private String TYPE="notetype";



	@Override
	public Notes updateNotes(Notes note) {
		
		return null;
	}



	@Override
	public List<Notes> searchNotes(String quary, Long noteId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Notes create(Notes note) {

		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = objectMapper.convertValue(note, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX,TYPE,String.valueOf(note.getNoteId())).source(dataMap);
		try {
			System.out.println("in");
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return note;
	}
	

	
	public Notes updateNote(Notes note) {
		System.out.println("Update");
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getNoteId()));
		@SuppressWarnings("unchecked")
		Map<String, Object> documentMapper = objectMapper.convertValue(note, Map.class);
		updateRequest.doc(documentMapper);
		try {
			@SuppressWarnings("unused")
			UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
			System.out.println("Update SuccessFully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return note;
	}
	
	@SuppressWarnings("unused")
	public void deleteNote(Long NoteId) {
		System.out.println("Delete");
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(NoteId));
		try {
			DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Notes> searchData(String query, String token) {
		
		Long userid = userToken.tokenVerify(token);
		
		SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.queryStringQuery("*" + query + "*").analyzeWildcard(true).field("title")
				.field("description"));
				//.filter(QueryBuilders.termsQuery("userId", String.valueOf(userid)));
		System.out.println();
		searchSourceBuilder.query(queryBuilder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			System.out.println(searchResponse);
		} catch (IOException e) {

			e.printStackTrace();
		}

		List<Notes> allnote = getSearchResult(searchResponse);

		return allnote;
	}

	private List<Notes> getSearchResult(SearchResponse response) {
		SearchHit[] searchHits = response.getHits().getHits();
		List<Notes> notes = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			notes.add(objectMapper.convertValue(hit.getSourceAsMap(), Notes.class));
		} 
		System.out.println(notes);
		return notes;
	}



	@Override
	public Notes createNote(Notes note) {
		// TODO Auto-generated method stub
		return null;
	}



//	@Override
//	public Notes createNote(Notes note) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
