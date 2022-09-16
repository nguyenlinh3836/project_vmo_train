package com.example.project_vmo.commons.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

public class MapperUtil {
  private static final ModelMapper modelMapper = new ModelMapper();



  public static <S, T> T map(S source, Class<T> targetClass) {
    return modelMapper.map(source, targetClass);
  }

  public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {

    return source
        .stream()
        .map(element -> modelMapper.map(element, targetClass))
        .collect(Collectors.toList());
  }
}
