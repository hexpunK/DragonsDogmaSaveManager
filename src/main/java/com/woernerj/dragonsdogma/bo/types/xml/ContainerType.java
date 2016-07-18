package com.woernerj.dragonsdogma.bo.types.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public abstract class ContainerType<T extends NamedType> extends NamedType {

	@XmlElement
	public List<? extends NamedType> children = new ArrayList<>();
}
