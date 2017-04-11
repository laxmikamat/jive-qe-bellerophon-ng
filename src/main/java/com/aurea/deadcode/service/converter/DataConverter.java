package com.aurea.deadcode.service.converter;

public interface DataConverter<ModelType, DtoType> {

    DtoType model2Dto(ModelType model);

    ModelType dto2Model(DtoType dto);
}
