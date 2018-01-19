package org.asyou.sequoia.common;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by steven on 17/9/22.
 */
public class Format {
    public static final String TEMPLATE_DATE = "yyyy-MM-dd";
    public static final String TEMPLATE_DATE_SECOND = "yyyy-MM-dd HH.mm.ss";
    public static final String TEMPLATE_DATETIME_S3 = "yyyy-MM-dd HH.mm.ss.SSS";
    public static final String TEMPLATE_DATETIME_S6 = "yyyy-MM-dd HH.mm.ss.SSSSSS";

    public static final String TEMPLATE_DATE_SECOND_BSON = "yyyy-MM-dd-HH.mm.ss";
    public static final String TEMPLATE_DATETIME_S3_BSON = "yyyy-MM-dd-HH.mm.ss.SSS";
    public static final String TEMPLATE_DATETIME_S6_BSON = "yyyy-MM-dd-HH.mm.ss.SSSSSS";

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(TEMPLATE_DATE);
    public static final DateTimeFormatter dateTimeFormatterSecond = DateTimeFormatter.ofPattern(TEMPLATE_DATE_SECOND);
    public static final DateTimeFormatter dateTimeFormatterS3 = DateTimeFormatter.ofPattern(TEMPLATE_DATETIME_S3);
    public static final DateTimeFormatter dateTimeFormatterS6 = DateTimeFormatter.ofPattern(TEMPLATE_DATETIME_S6);

    public static final DateTimeFormatter dateTimeFormatterSecond_Bson = DateTimeFormatter.ofPattern(TEMPLATE_DATE_SECOND_BSON);
    public static final DateTimeFormatter dateTimeFormatterS3_Bson = DateTimeFormatter.ofPattern(TEMPLATE_DATETIME_S3_BSON);
    public static final DateTimeFormatter dateTimeFormatterS6_Bson = DateTimeFormatter.ofPattern(TEMPLATE_DATETIME_S6_BSON);

    public static SimpleDateFormat simpleDateFormat() {return new SimpleDateFormat(TEMPLATE_DATE);}
    public static SimpleDateFormat simpleDateTimeFormatSecond() {return new SimpleDateFormat(TEMPLATE_DATE_SECOND);}
    public static SimpleDateFormat simpleDateTimeFormatS3() {return new SimpleDateFormat(TEMPLATE_DATETIME_S3);}
    public static SimpleDateFormat simpleDateTimeFormatS6() {return new SimpleDateFormat(TEMPLATE_DATETIME_S6);}
    public static SimpleDateFormat simpleDateTimeFormatSecond_Bson() {return new SimpleDateFormat(TEMPLATE_DATE_SECOND_BSON);}
    public static SimpleDateFormat simpleDateTimeFormatS3_Bson() {return new SimpleDateFormat(TEMPLATE_DATETIME_S3_BSON);}
    public static SimpleDateFormat simpleDateTimeFormatS6_Bson() {return new SimpleDateFormat(TEMPLATE_DATETIME_S6_BSON);}

}
