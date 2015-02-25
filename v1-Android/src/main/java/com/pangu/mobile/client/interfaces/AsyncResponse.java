package com.pangu.mobile.client.interfaces;

import com.pangu.mobile.client.models.InformationModel;

/**
 * @author Mark Goddard
 * @date 24/02/15
 * @desc Interface for Async-task (Pangu Connection).
 */
public interface AsyncResponse {
    void processData(InformationModel im);
}