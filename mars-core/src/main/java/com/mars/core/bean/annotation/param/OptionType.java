package com.mars.core.bean.annotation.param;

/**
 * Created by lixl on 2016/12/20.
 */
public enum OptionType {
    /** = ,等于运算符 */
    EQ,
    /** != ,不等于运算符*/
    NEQ,

    /** > ,大于 运算符 */
    GT,

    /** < ,小于 运算符 */
    LT,

    /**  >= ,大于等于 运算符 */
    GTE,

    /** <= ,小于等于运算符 */
    LTE,
    /** %like% , 全模糊匹配 */
    LIKE,
    /** %like , 左模糊匹配 */
    LLIKE,
    /** like% , 右模糊匹配 */
    RLIKE,
    /** OR , 或者 */
    OR,
    /** ORLIKE , 或者像*/
    ORLIKE,
    /** IN , 在其中 */
    IN

}
