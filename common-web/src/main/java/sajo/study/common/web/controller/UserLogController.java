package sajo.study.common.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sajo.study.common.core.dto.UserLogDTO;
import sajo.study.common.core.model.UserLog;
import sajo.study.common.web.service.UserLogService;

@RestController
@RequestMapping("/api/userlog")
public class UserLogController extends BaseApiController<UserLog, UserLogDTO> {
	public UserLogController(UserLogService service) {
		super(service);
	}
}
