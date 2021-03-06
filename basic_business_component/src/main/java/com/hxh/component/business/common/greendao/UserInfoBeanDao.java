package com.hxh.component.business.common.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hxh.component.business.common.UserInfoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO_BEAN".
*/
public class UserInfoBeanDao extends AbstractDao<UserInfoBean, Long> {

    public static final String TABLENAME = "USER_INFO_BEAN";

    /**
     * Properties of entity UserInfoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Expires_in = new Property(1, int.class, "expires_in", false, "EXPIRES_IN");
        public final static Property Token_type = new Property(2, String.class, "token_type", false, "TOKEN_TYPE");
        public final static Property Access_token = new Property(3, String.class, "access_token", false, "ACCESS_TOKEN");
        public final static Property Refresh_token = new Property(4, String.class, "refresh_token", false, "REFRESH_TOKEN");
        public final static Property User_str = new Property(5, String.class, "user_str", false, "USER_STR");
        public final static Property Tenant_str = new Property(6, String.class, "tenant_str", false, "TENANT_STR");
        public final static Property Customer_str = new Property(7, String.class, "customer_str", false, "CUSTOMER_STR");
        public final static Property Scope = new Property(8, String.class, "scope", false, "SCOPE");
    }


    public UserInfoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"EXPIRES_IN\" INTEGER NOT NULL ," + // 1: expires_in
                "\"TOKEN_TYPE\" TEXT," + // 2: token_type
                "\"ACCESS_TOKEN\" TEXT," + // 3: access_token
                "\"REFRESH_TOKEN\" TEXT," + // 4: refresh_token
                "\"USER_STR\" TEXT," + // 5: user_str
                "\"TENANT_STR\" TEXT," + // 6: tenant_str
                "\"CUSTOMER_STR\" TEXT," + // 7: customer_str
                "\"SCOPE\" TEXT);"); // 8: scope
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfoBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getExpires_in());
 
        String token_type = entity.getToken_type();
        if (token_type != null) {
            stmt.bindString(3, token_type);
        }
 
        String access_token = entity.getAccess_token();
        if (access_token != null) {
            stmt.bindString(4, access_token);
        }
 
        String refresh_token = entity.getRefresh_token();
        if (refresh_token != null) {
            stmt.bindString(5, refresh_token);
        }
 
        String user_str = entity.getUser_str();
        if (user_str != null) {
            stmt.bindString(6, user_str);
        }
 
        String tenant_str = entity.getTenant_str();
        if (tenant_str != null) {
            stmt.bindString(7, tenant_str);
        }
 
        String customer_str = entity.getCustomer_str();
        if (customer_str != null) {
            stmt.bindString(8, customer_str);
        }
 
        String scope = entity.getScope();
        if (scope != null) {
            stmt.bindString(9, scope);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfoBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getExpires_in());
 
        String token_type = entity.getToken_type();
        if (token_type != null) {
            stmt.bindString(3, token_type);
        }
 
        String access_token = entity.getAccess_token();
        if (access_token != null) {
            stmt.bindString(4, access_token);
        }
 
        String refresh_token = entity.getRefresh_token();
        if (refresh_token != null) {
            stmt.bindString(5, refresh_token);
        }
 
        String user_str = entity.getUser_str();
        if (user_str != null) {
            stmt.bindString(6, user_str);
        }
 
        String tenant_str = entity.getTenant_str();
        if (tenant_str != null) {
            stmt.bindString(7, tenant_str);
        }
 
        String customer_str = entity.getCustomer_str();
        if (customer_str != null) {
            stmt.bindString(8, customer_str);
        }
 
        String scope = entity.getScope();
        if (scope != null) {
            stmt.bindString(9, scope);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserInfoBean readEntity(Cursor cursor, int offset) {
        UserInfoBean entity = new UserInfoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // expires_in
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // token_type
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // access_token
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // refresh_token
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // user_str
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // tenant_str
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // customer_str
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // scope
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfoBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setExpires_in(cursor.getInt(offset + 1));
        entity.setToken_type(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAccess_token(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRefresh_token(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUser_str(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTenant_str(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCustomer_str(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setScope(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserInfoBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserInfoBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfoBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
