package com.github.ricardocomar.springcharon.appcharon.fixture;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.github.ricardocomar.springcharon.model.PurchaseAvro;
import com.github.ricardocomar.springcharon.model.PurchaseAvroStatus;
import com.github.ricardocomar.springcharon.model.PurchaseItemAvro;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class PurchaseAvroFixture implements TemplateLoader {


	@Override
	public void load() {
		Fixture.of(PurchaseAvro.class).addTemplate("valid", new Rule() {
			{
				add("transaction", "TRANPURC-1");
				add("id", "purc-10");
				add("customer", "Lala Silva");
				add("syncTransaction", "AA");
				add("syncSequence", "10");
				add("syncKey", "AAA-123");
			    add("totalValue", new BigDecimal(500.00 + 3900.00 + 11500.00).setScale(2, RoundingMode.HALF_EVEN));
				add("status", PurchaseAvroStatus.OPEN);
				add("date", LocalDateTimeFixture.LDF_10_DAYS_PAST.format(LocalDateTimeFixture.TIME_FORMATTER));
				add("items", has(3).of(PurchaseItemAvro.class, "g6play", "iphone10", "iphone11pro"));
			}
		});
		Fixture.of(PurchaseAvro.class).addTemplate("over6").inherits("valid", new Rule() {
			{
				add("totalValue",
						new BigDecimal((500.00 + 3900.00 + 11500.00) * 2.0).setScale(2, RoundingMode.HALF_EVEN));
				add("items", has(6).of(PurchaseItemAvro.class, "g6play", "iphone10", "iphone11pro"));
			}
		});
	}

}
