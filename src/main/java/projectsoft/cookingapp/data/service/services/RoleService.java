package projectsoft.cookingapp.data.service.services;

import projectsoft.cookingapp.data.service.models.RoleServiceModel;

public interface RoleService  {
    void  seedRolesInDb();

    RoleServiceModel findByAuthority(String authority);

}
