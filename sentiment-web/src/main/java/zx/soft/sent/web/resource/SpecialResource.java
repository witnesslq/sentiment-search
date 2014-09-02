package zx.soft.sent.web.resource;

import java.util.ArrayList;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.domain.special.SpecialTopic;
import zx.soft.sent.web.application.SpecialApplication;
import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.sent.web.utils.URLCodecUtils;

/**
 * 专题资源类
 * 
 * @author wanggang
 *
 */
public class SpecialResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SpecialResource.class);

	private SpecialApplication application;

	private String identify = "";
	private String type = "";

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (SpecialApplication) getApplication();
		identify = (String) this.getRequest().getAttributes().get("identify");
		type = (String) this.getRequest().getAttributes().get("type");
	}

	@Post("json")
	public Object addSpecialTopics(final SpecialTopics specialTopics) {
		application.insertSpecialInfos(specialTopics);
		return new ErrorResponse.Builder(0, "ok").build();
	}

	@Get
	public String getSpecialResult() {
		if (identify == null || identify.length() == 0 || type == null || type.length() == 0) {
			logger.error("Params `identify` or `type` is null.");
			return null;
		}
		return application.selectSpecialResult(identify, type);
	}

	@Delete
	public Object rmSpecialTopic() {
		if (identify == null || identify.length() == 0) {
			logger.error("Param `identify` is null.");
			return null;
		}
		application.deleteSpecialInfo(identify);
		return new ErrorResponse.Builder(0, "ok").build();
	}

	/**
	 * 解决Jackson无法识别java.util.Collection问题。
	 *
	 */
	public static class SpecialTopics extends ArrayList<SpecialTopic> {

		private static final long serialVersionUID = 1144770130427640340L;

	}

}