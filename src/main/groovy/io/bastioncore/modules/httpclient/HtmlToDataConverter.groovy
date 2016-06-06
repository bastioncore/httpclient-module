package io.bastioncore.modules.httpclient

import io.bastioncore.core.converters.AbstractConverter
import org.cyberneko.html.parsers.DOMParser
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.xml.sax.InputSource

@Component
@Scope('singleton')
class HtmlToDataConverter extends AbstractConverter {

    @Override
    Object convert(Object o) {
        final DOMParser domParser = new DOMParser()
        InputSource inputSource = new InputSource(new StringReader(o));
        domParser.parse(inputSource)
        return domParser.getDocument()
    }
}
