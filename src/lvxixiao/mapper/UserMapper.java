package lvxixiao.mapper;

import lvxixiao.pojo.User;

public interface UserMapper {

	User findUserByuser(User user);

	void updateUser(User user);

}
