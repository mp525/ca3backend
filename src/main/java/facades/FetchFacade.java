package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CatFactDTO;
import dto.PersonCatDTO;
import dto.PersonDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.HttpUtils;

public class FetchFacade {

    class Default implements Callable<String> {

        String url;

        Default(String url) {
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            String raw = HttpUtils.fetchData(url);
            return raw;
        }
    }

    public PersonCatDTO fetchParallel() throws InterruptedException, ExecutionException {
        String[] hostList = {"https://api.namefake.com/", "https://meowfacts.herokuapp.com/"};
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>();
        List<String> retList = new ArrayList();

        for (String url : hostList) {
            Callable<String> urlTask = new Default(url);
            Future future = executor.submit(urlTask);
            futures.add(future);
        }
        CatFactDTO fact = null;
        PersonDTO person = null;
        
        for (Future<String> future : futures) {
            if(future.get().contains("name")){
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                person = gson.fromJson(future.get(), PersonDTO.class);
            }
            else{
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                fact = gson.fromJson(future.get(), CatFactDTO.class);
            }
        }
       
        PersonCatDTO both = new PersonCatDTO(person, fact);
        return both;
    }

}
