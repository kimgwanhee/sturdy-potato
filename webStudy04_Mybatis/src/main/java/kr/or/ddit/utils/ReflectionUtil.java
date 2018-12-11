package kr.or.ddit.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtil {
	
	public static List<Class<?>> getClassesAtBasePackageTraversing(String basePackage) throws Exception {
		List<Class<?>> classList = new ArrayList<>();
		URL baseURL = Thread.currentThread().getContextClassLoader().getResource("/" + basePackage.replace(".", "/"));
		Path start = Paths.get(baseURL.toURI());
		
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if(file.toString().endsWith(".class")) {
//					kr.or.ddit.board.controller.BoardDeleteController
					String temp = file.toString().substring(start.toString().length());
					String qualifiedName =  basePackage + temp.substring(0, temp.lastIndexOf('.'))
											.replace(File.separatorChar , '.');
					try {
						classList.add(Class.forName(qualifiedName));
					} catch (ClassNotFoundException e) {
						return FileVisitResult.CONTINUE;
					}
				}
				return super.visitFile(file, attrs);
			}
		});
		return classList;
	}
	
	public static List<Class<?>> getClassesAtBasePackage(String basePackage) {
		List<Class<?>> classList = new ArrayList<>();
		URL baseURL = Thread.currentThread().getContextClassLoader().getResource("/" + basePackage.replace(".", "/"));
		//Thread.currentThread()현재 활성화된 thread를 레퍼런스로 가져옴
		
		if(baseURL==null) return classList;
		
		File baseFolder = new File(baseURL.getFile());
		String[] classArray = baseFolder.list((dir, name) -> {
			boolean flag = false;
			flag = name.endsWith(".class");
			return flag;
		});

		if (classArray != null) {
			for (String classFileName : classArray) {
				int lastIndex = classFileName.lastIndexOf(".class");
				String qualifiedName = basePackage + "." + classFileName.substring(0, lastIndex);
				try {
					Class clz = Class.forName(qualifiedName);// class loading
					classList.add(clz);
				} catch (ClassNotFoundException e) {
					continue;
				}
			} // for end
		} // IF END
		return classList;
	}

	public static List<Class<?>> getClassesAtBasePackages(String... basePackages) throws Exception {
		// ...가변 파라미터->사실 안에서 가지고놀땐 배열로 가지고 논다
		List<Class<?>> classList = new ArrayList<>();
		if (basePackages != null) {
			for (String basePackage : basePackages) {
				classList.addAll(getClassesAtBasePackageTraversing(basePackage));
			}
		}
		return classList;
	}

	public static List<Class<?>> getClassesWithAnnotationAtBasePackages(Class<? extends Annotation> annotationType,
			String... basePackages) throws Exception {
		List<Class<?>> classList = getClassesAtBasePackages(basePackages);
		for (int idx = classList.size() - 1; idx >= 0; idx--) {
			Class<?> temp = classList.get(idx);
			if (temp.getAnnotation(annotationType) == null) {
				classList.remove(idx);
			}
		}
		return classList;
	}

	public static List<Method> getMethodsAtClass(Class<?> targetClz, Class<?> returnType, Class<?>... parameterTypes) {
		List<Method> methodList = new ArrayList<>();
		if (targetClz != null) {// 타켓클레스가 넘어왔다는것
			methodList.addAll(Arrays.asList(targetClz.getDeclaredMethods()));// asList-배열을 리스트로 바꿔줌
			// getDeclaredMethods()-모든 메서드를 가져올수있음 methods의 단점보완
			for (int idx = methodList.size()-1; idx >= 0; idx--) {
				Method temp = methodList.get(idx);
				// 리턴타입이 동일하다면 ..
				if ((returnType != null && !returnType.equals(temp.getReturnType())
						&& (parameterTypes != null && !Arrays.deepEquals(parameterTypes, temp.getParameterTypes())))) {
					methodList.remove(idx);
				}
			}
		}
		return methodList;
	}

	public static List<Method> getMethodsWithAnnotationAtClass(Class<?> targetClz,
			Class<? extends Annotation> annotationType, Class<?> returnType, Class<?>...parameterTypes) {
		List<Method> methodList = getMethodsAtClass(targetClz, returnType, parameterTypes);
		for (int idx = methodList.size() - 1; idx >= 0; idx--) {
			Method temp = methodList.get(idx);
			if (temp.getAnnotation(annotationType) == null) {// 없다면
				methodList.remove(idx);
			}
		}

		return methodList;
	}
}
