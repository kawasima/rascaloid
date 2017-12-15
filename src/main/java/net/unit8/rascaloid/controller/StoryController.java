package net.unit8.rascaloid.controller;

import enkan.collection.Parameters;
import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import enkan.security.UserPrincipal;
import net.unit8.rascaloid.dao.StoryDao;
import net.unit8.rascaloid.entity.Story;

import javax.inject.Inject;
import java.util.List;

public class StoryController {
    @Inject
    private DomaProvider daoProvider;

    @Inject
    private BeansConverter beansConverter;

    public List<Story> list(Parameters params, UserPrincipal principal) {
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        return storyDao.findAll(params.getLong("projectId"), principal);
    }

}
