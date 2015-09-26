package org.networking.service;

import org.networking.entity.SalesOrder;

/**
 * Created by Gino on 9/21/2015.
 */
public interface SalesOrderService extends BaseService<SalesOrder> {

    void setPoints(SalesOrder order);
}
