package com.slv.slv_api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.slv.pojos.Add;
import com.slv.pojos.Move;


public class test {

	public static void main(String[] args) throws JsonProcessingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
        JsonNode source = mapper.readTree("{\"attribute1\":{\"1.att1\":\"valeur1\"},\"attribute2\":null,\"attribute7\":null}");
        JsonNode target = mapper.readTree("{\"attribute3\":null,\"attribute1\":{\"1.att2\":\"valeur1\",\"1.att3\":\"valeur1\"},\"attribute1bis\":null,\"attribute2\":\"yiyi\"}");
        
        JsonNode patch = JsonDiff.asJson(source, target);
        
     
        Iterator<JsonNode> opIterator = patch.elements();
        List<Move>  moves = new ArrayList<>();
        List<Add>  adds = new ArrayList<>();
        while (opIterator.hasNext()) {
            JsonNode opNode = opIterator.next();
            if((opNode.get("op").asText()=="move")){

            	moves.add(new Move(opNode.get("op").asText(), opNode.get("path").asText(), opNode.get("from").asText()));
            }else if(opNode.get("op").asText()=="add"){
            	adds.add(new Add(opNode.get("op").asText(),  opNode.get("path").asText(), opNode.get("value").asText()));
            }
    
            
       /*     System.out.println(opNode.get("op"));
            System.out.println(opNode.get("path"));
            System.out.println(opNode.get("from"));
            System.out.println(opNode.get("value"));*/
        }
        
        for(Add add : adds) {
        	System.out.println("la valeur "+add.getValue() +" a été ajouté à "+add.getPath());
        }
        for(Move move : moves){
        	
        	System.out.println("la valeur "+move.getPath()+ " a été ajouté.");
        	System.out.println("la valeur "+move.getFrom()+ " a été supprimé.");
        }
       
        
        
        

      System.out.println("Differences : " + patch);

	}

}
