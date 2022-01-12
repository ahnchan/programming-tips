# Introduction
두개의 Base 객체를 비교하여 값이 다른 부분을 찾는다.

# Problem
값이 변경된 부분을 찾아서 감사(Audit) 정보를 따로 저장하려고 한다. 어떤 정보가 이전에 어떤 값(before)에서 어떤 값(After)으로 변경되었는지 찾아야 한다.

# Solving
## Idea
JSON 혹은 Map 으로 Key, Value 형태의 정보를 구성하면 두개의 값들을 비교하여 찾을수 있을 것이다.

Google Guava 의 MapDifference 를 이용하면 Map<String, Object>의 형태의 두개의 값을 비교하여 key, before(left), after(right)로 분리해서 결과를 표시한다.
비교할 값이 일부일 경우도 생각해야한다.
```java
    MapDifference<String, Object> resultMap = Maps.difference(firstMap, secondMap);
```

## PROBLEM 
비교할 객체중에 제외해야할 부분이 있을 수 있다. 단순히 내부적으로 사용하는 값이여서 데이터 비교대상에서 제외를 해야한다.
Gson 을 이용하여 객체를 Map<String, Object>로 변경할때 strategy 를 구성하여 특정 Annotation 은 컨버전에서 제외할수 있다.  

```java
		// Exclude strategy
		ExclusionStrategy strategy = new ExclusionStrategy() {
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}

			@Override
			public boolean shouldSkipField(FieldAttributes field) {
				return field.getAnnotation(ExcludeAudit.class) != null;
			}
		};

		// gson builder with strategy
		Gson gson = new GsonBuilder()
				.setExclusionStrategies(strategy)
				.create();

		String strJson = gson.toJson(base);
```


# 문제점/개선할 부분
객체내의 Array는 각 Array별로 구분하여 비교하지를 못한다. 


# Libraries
[Gson](https://github.com/google/gson): Object를 JSON String 으로 컨버전한다.
[Jackson](https://github.com/FasterXML/jackson): JSON String을 Map<String, Object>로 컨버전한다.
[Guava](https://github.com/google/guava): MapDifference를 이용하여 두개의 Map<String, Object>를 비교하여 차이점을 분석한다.


# 다른 Libraries
아래 Open Source들은 before, after 값을 추출하지 못하고 변경된 부분만 찾아줬다.

[zjsonpatch](https://github.com/flipkart-incubator/zjsonpatch)
```java
		JsonNode firstJson = convertJson(first);
        JsonNode secondJson = convertJson(second);

		JsonNode patch = JsonDiff.asJson(firstJson, secondJson);
```
[Json Patch](https://github.com/java-json-tools/json-patch)
```java
		JsonNode firstJson = convertJson(first);
		JsonNode secondJson = convertJson(second);

		JsonPatch patch = com.github.fge.jsonpatch.diff.JsonDiff.asJsonPatch(firstJson, secondJson);
		JsonNode patchNode = com.github.fge.jsonpatch.diff.JsonDiff.asJson(firstJson, secondJson);
```
