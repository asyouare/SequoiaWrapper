package org.asyou.sequoia.query;

import org.bson.BSONObject;

import java.io.Serializable;

/**
 * Created by steven on 17/7/14.
 */
public interface IQuery extends Cloneable,Serializable {
    BSONObject toBSONObject();
}
