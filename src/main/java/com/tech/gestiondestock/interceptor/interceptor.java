package com.tech.gestiondestock.interceptor;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class interceptor extends EmptyInterceptor{

//	@Override
//	public String onPrepareStatement(String sql) {
//		if(StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")){
//			//System.out.println(String.format("sql = %s", sql));
//			//sql = sql.contains("where") ? sql + " AND codearticle = 'string'": sql + " WHERE codearticle = 'Article 2'";
//			//sql = sql + " WHERE codeArticle = 'string";
//		}
//		return super.onPrepareStatement(sql);
//	}
	
	@Override
	  public String onPrepareStatement(String sql) {
	    if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
	      // select utilisateu0_.
	      final String entityName = sql.substring(7, sql.indexOf("."));
	      final String idEntreprise = MDC.get("idEntreprise");
	      if (StringUtils.hasLength(entityName)
	          && !entityName.toLowerCase().contains("entreprise")
	          && !entityName.toLowerCase().contains("roles")
	          && StringUtils.hasLength(idEntreprise)) {

	        if (sql.contains("where")) {
	          sql = sql + " and " + entityName + ".identreprise = " + idEntreprise;
	        } else {
	          sql = sql + " where " + entityName + ".identreprise = " + idEntreprise;
	        }
	      }
	    }
	    return super.onPrepareStatement(sql);
	  }
}
