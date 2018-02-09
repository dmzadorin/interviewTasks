package ru.dmzadorin.interview.tasks.pyramid.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class that represents response to the client. Content of this class is converted to JSON
 * Created by @Dmitry Zadorin on 12.02.2016.
 */
public class WeightResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double weight;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String descriptionMessage;

    /**
     * Returns calculated weight
     *
     * @return calculated weight
     */
    public Double getWeight() {
        return weight;
    }


    /**
     * Sets calculated weight
     *
     * @param weight weight to set
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * Returns additional description message, if any
     *
     * @return description message
     */
    public String getDescriptionMessage() {
        return descriptionMessage;
    }

    /**
     * Sets additional description message
     *
     * @param descriptionMessage message to set
     */
    public void setDescriptionMessage(String descriptionMessage) {
        this.descriptionMessage = descriptionMessage;
    }

    /**
     * Creates new instance of response with weight param, leaving description blank
     *
     * @param weight weight to set
     * @return new instance of WeightResponse
     */
    public static WeightResponse from(double weight) {
        WeightResponse response = new WeightResponse();
        response.setWeight(weight);
        return response;
    }

    /**
     * Creates new instance of response with 0.0 weight param and description message
     *
     * @param descriptionMessage message to set
     * @return new instance of WeightResponse
     */
    public static WeightResponse from(String descriptionMessage) {
        WeightResponse response = new WeightResponse();
        response.setDescriptionMessage(descriptionMessage);
        return response;
    }
}
