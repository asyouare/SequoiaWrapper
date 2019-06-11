package org.asyou.sequoia.query;

import org.bson.BSONObject;

/**
 * Created by steven on 17/7/14.
 */
public interface IQuery extends Cloneable{
    BSONObject toBSONObject();
}
