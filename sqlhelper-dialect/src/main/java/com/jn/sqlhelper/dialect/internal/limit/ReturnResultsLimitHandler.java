/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the LGPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at  http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jn.sqlhelper.dialect.internal.limit;

import com.jn.sqlhelper.dialect.RowSelection;

/**
 * select * from table return results $limit
 * select * from table return results $offset TO $limit
 * <p>
 * every dialect use the limitHandler should set bindLimitParameterInReverseOrder = false
 */
public class ReturnResultsLimitHandler extends AbstractLimitHandler {
    @Override
    public String processSql(String sql, RowSelection rowSelection) {
        return getLimitString(sql, LimitHelper.getFirstRow(rowSelection), getMaxOrLimit(rowSelection));
    }

    @Override
    protected String getLimitString(String sql, long offset, int limit) {
        boolean hasOffset = offset > 0;
        if (getDialect().isUseLimitInVariableMode()) {
            return sql + " RETURN RESULT " + (hasOffset ? " ? TO ?" : " ?");
        } else {
            return sql + " RETURN RESULT " + (hasOffset ? (offset + " TO ") : "") + limit;
        }
    }
}
