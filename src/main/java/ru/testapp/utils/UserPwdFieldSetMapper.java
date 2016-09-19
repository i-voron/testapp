package ru.testapp.utils;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class UserPwdFieldSetMapper implements FieldSetMapper<UserPwd> {
    @Override
    public UserPwd mapFieldSet(FieldSet fieldSet) throws BindException {
        UserPwd userPwd=new UserPwd();
        userPwd.setId(fieldSet.readInt(0));
        userPwd.setHash(fieldSet.readString(1));
        return userPwd;
    }
}
