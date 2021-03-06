package com.github.ricardocomar.springcharon.appcharon.fixture;

import java.util.UUID;

import com.github.ricardocomar.springcharon.appcharon.consumer.model.RequestMessage;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class RequestMessageFixture implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(RequestMessage.class).addTemplate("purchase", new Rule() {
			{
				final String date = LocalDateTimeFixture.LDF_10_DAYS_PAST.format(LocalDateTimeFixture.TIME_FORMATTER);

				add("id", UUID.randomUUID().toString());
				add("origin", "TestSuite");
				add("trancode",
						"TRANPURC-1" + "purc-10     Lala Silva                    1590000   OPEN      " + date
								+ "123-45-6    sku123456 Motorola G6 Play  50000     "
								+ "654-32-1    sku654321 Iphone 10         390000    "
								+ "999-99-9    sku999999 Iphone 11 Pro     1150000   "
				/*
				 * + "                                                  "
				 */
								);
			}
		});

	}

}
