package com.base.controllerData;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import com.alibaba.druid.proxy.jdbc.ClobProxyImpl;

/**
 * 对象功能:参数封装Map:用于封装参数和Mysql数据对象
 */
@SuppressWarnings("rawtypes")
public class PageData extends HashMap implements Map {

	private static final long serialVersionUID = 1L;

	Map map = null;
	HttpServletRequest request;

	@SuppressWarnings("unchecked")
	public PageData(HttpServletRequest request) {
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		map = returnMap;
	}

	public PageData() {
		map = new HashMap();
	}

	public PageData(Object key,Object value) {
		map = new HashMap();
		put(key, value);
	}

	@Override
	public Object get(Object key) {
		Object obj = null;
		if (map.get(key) instanceof Object[]) {
			Object[] arr = (Object[]) map.get(key);
			obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}

	public String getString(Object key) {
		Object val = get(key);
		if (val != null && !"".equals(val)) {
			return val.toString();
		} else {
			return "";
		}
	}

	public Double getDouble(Object key) {
		Object val = get(key);
		if (val != null && !"".equals(val)) {
			return Double.valueOf(val.toString());
		} else {
			return 0d;
		}
	}

	public Integer getInt(Object key) {
		Object val = get(key);
		if (val != null && !"".equals(val)) {
			return Integer.valueOf(val.toString());
		} else {
			return null;
		}
	}

	public Boolean getBoolean(Object key) {
		Object val = get(key);
		if (val != null && !"".equals(val)) {
			return Boolean.parseBoolean(val.toString());
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		if (value instanceof ClobProxyImpl) { // 读取oracle Clob类型数据
			try {
				ClobProxyImpl cpi = (ClobProxyImpl) value;
				Reader is = cpi.getCharacterStream(); // 获取流
				BufferedReader br = new BufferedReader(is);
				String str = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (str != null) { // 循环读取数据拼接到字符串
					sb.append(str);
					sb.append("\n");
					str = br.readLine();
				}
				value = sb.toString();
			} catch (Exception e) {
			}
		}
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set keySet() {
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(Map t) {
		map.putAll(t);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection values() {
		return map.values();
	}

}
