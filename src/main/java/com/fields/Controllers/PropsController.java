package com.fields.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fields.Models.Props;
import com.fields.Repositories.PropsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1/props")
public class PropsController {


    @Autowired
    private PropsRepository propsRepository;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Props> getRegProds(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Props props = this.propsRepository.findOne(id);
        if (props == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        JsonNode obj = props.fields;
        return new ResponseEntity<>(props, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<Props>> findAllBySpecification(@RequestParam(name = "id",required=false) Long id
    ) {
        List<Props> props = propsRepository.findAll();
        if (id != null)
            props = propsRepository.getById(id);
        return new ResponseEntity<>(props, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getValue", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<JsonNode> findValueByName(@RequestParam(name = "fieldName", required=false)String fieldName
            , @RequestParam(name = "refName", required=false)String refName
            , @RequestParam(name = "fieldValue", required=false)String fieldValue
    ) {
        List<Props> props = propsRepository.findAll();
        if (refName != null)
            props =  propsRepository.getByRefName(refName);
        List<JsonNode> nodeResult = new ArrayList<>();

        for (final Props prop : props) {
            List<JsonNode> nodesFor = null;
            if (fieldName != null) {
                nodesFor = prop.fields.findParents(fieldName);
            }
            if (fieldValue != null && nodesFor != null) {
                nodesFor.removeIf((JsonNode p) -> !p.get(fieldName).asText().equals(fieldValue));
            }
            if (nodesFor != null && nodesFor.size() > 0)
                for (JsonNode node:nodesFor)
                   nodeResult.add(nodesFor.get(0));
            }
        if ((nodeResult == null || nodeResult.size() == 0)) {
            ObjectMapper maper = new ObjectMapper();
            nodeResult = maper.convertValue(props, List.class);
        }
        return nodeResult;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Props> saveProperties(@RequestBody @Valid Props props){
        HttpHeaders headers = new HttpHeaders();
        if (props == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.propsRepository.save(props);

        return new ResponseEntity<>(props, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteRegProds(@PathVariable("id") Long id){

        Props props = this.propsRepository.findOne(id);
        ResponseEntity<Object> response;
        if (props == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            this.propsRepository.delete(id);
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

}
