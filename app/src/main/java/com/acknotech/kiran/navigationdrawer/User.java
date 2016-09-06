package com.acknotech.kiran.navigationdrawer;

/**
 * Created by kiran on 18/8/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class User {

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("secGameIds")
    @Expose
    private List<Integer> secGameIds = new ArrayList<Integer>();
    @SerializedName("roleId")
    @Expose
    private Integer roleId;

    public String getFirstName() {
        return firstName;
    }

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
     * The workids
     */
    public List<Integer> getSecGameIds() {
        return secGameIds;
    }

    /**
     *
     * @param workids
     * The workids
     */
    public void setSecGameIds(List<Integer> workids) {
        this.secGameIds = workids;
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