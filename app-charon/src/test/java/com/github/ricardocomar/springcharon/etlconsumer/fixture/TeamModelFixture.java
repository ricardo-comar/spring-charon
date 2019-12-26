package com.github.ricardocomar.springcharon.etlconsumer.fixture;

import com.github.ricardocomar.springcharon.appcharon.model.Employee;
import com.github.ricardocomar.springcharon.appcharon.model.Team;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class TeamModelFixture implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Team.class).addTemplate("valid", new Rule() {
			{
				add("transaction", "TRANTEAM-1");
				add("teamName", "Team ABC");
				add("employees", has(3).of(Employee.class, "boss", "dev1", "dev2"));
			}
		});

	}

}
