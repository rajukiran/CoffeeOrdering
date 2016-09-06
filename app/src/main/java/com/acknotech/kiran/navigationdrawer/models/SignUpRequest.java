package com.acknotech.kiran.navigationdrawer.models;

/**
 * Created by kiran on 20/8/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SignUpRequest {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("date")
    @Expose
    private String dateOfBirth;
    @SerializedName("phoneNum")
    @Expose
    private String phoneNum;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("secGameIds")
    @Expose
    private List<Integer> secGameIds = new ArrayList<Integer>();

    private int gameId;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;

    public int getGameId(){
        return gameId;
    }

    public void setGameId(int gameId){
        this.gameId = gameId;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @param dateOfBirth
     * The date
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @return
     * The phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     *
     * @param phoneNum
     * The phoneNum
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The secGameIds
     */
    public List<Integer> getSecGameIds() {
        return secGameIds;
    }

    /**
     *
     * @param secGameIds
     * The secGameIds
     */
    public void setSecGameIds(List<Integer> secGameIds) {
        this.secGameIds = secGameIds;
    }

    /**
     *
     * @return
     * The roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     *
     * @param roleId
     * The roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}