package ru.testapp.utils;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import java.io.*;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;

public class CustomFileReader {
    private File file;
    private static Semaphore semaphore = new Semaphore(1);

    public String findByLogin2(int id) {
        FlatFileItemReader<UserPwd> itemReader = new FlatFileItemReader<UserPwd>();
        itemReader.setResource(new FileSystemResource(getFile()));

        DefaultLineMapper<UserPwd> lineMapper = new DefaultLineMapper<UserPwd>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer("\t"));
        itemReader.setLineMapper(lineMapper);
        lineMapper.setFieldSetMapper(new UserPwdFieldSetMapper());
        itemReader.open(new ExecutionContext());
        try {
            UserPwd userPwd;
            while ((userPwd = itemReader.read())!=null){
                if(userPwd.getId()==id){
                    return userPwd.getHash();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            itemReader.close();
        }

        return null;
    }
    public String findByLogin(int id) {
        String userid=null;
        String hash=null;
        try {
            semaphore.acquire();
            try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))){
                String line;
                StringTokenizer st=null;
                while ((line = reader.readLine()) != null) {
                    st=new StringTokenizer(line,"\t");
                    while (st.hasMoreElements()) {
                        userid = (String) st.nextElement();
                        hash = (String) st.nextElement();
                    }
                    if(Integer.parseInt(userid)==id){
                        break;
                    }else{
                        hash=null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
        return hash;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
