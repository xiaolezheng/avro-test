package com.lxz.service.avro;

import com.lxz.model.avro.User;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;

/**
 * Created by xiaolezheng on 16/4/18.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        User user1 = new User();
        user1.setName("Alyssa");
        user1.setFavoriteNumber(256);

        User user2 = new User("Ben", 7, "red");

        User user3 = User.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(null)
                .build();

        // Serializing
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
        dataFileWriter.create(user1.getSchema(), new File("users.avro"));
        dataFileWriter.append(user1);
        dataFileWriter.append(user2);
        dataFileWriter.append(user3);
        dataFileWriter.close();

        // Deserialize Users from disk
        DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
        DataFileReader<User> dataFileReader = new DataFileReader<User>(new File("users.avro"), userDatumReader);
        User user = null;
        while (dataFileReader.hasNext()) {//
            user = dataFileReader.next(user);
            System.out.println(user);
        }
    }
}
