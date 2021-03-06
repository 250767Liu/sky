package jc.sky;

import android.app.Application;
import android.os.Looper;

import java.io.IOException;
import java.util.List;

import jc.sky.core.SKYIBiz;
import jc.sky.core.SKYICommonBiz;
import jc.sky.core.SynchronousExecutor;
import jc.sky.core.exception.SKYHttpException;
import jc.sky.display.SKYIDisplay;
import jc.sky.modules.DaggerSKYIComponent;
import jc.sky.modules.SKYModule;
import jc.sky.modules.SKYModulesManage;
import jc.sky.modules.contact.SKYIContact;
import jc.sky.modules.download.SKYDownloadManager;
import jc.sky.modules.file.SKYFileCacheManage;
import jc.sky.modules.methodProxy.SKYMethods;
import jc.sky.modules.screen.SKYScreenManager;
import jc.sky.modules.structure.SKYStructureIManage;
import jc.sky.modules.threadpool.SKYThreadPoolManager;
import jc.sky.modules.toast.SKYToast;
import jc.sky.view.common.SKYIViewCommon;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author sky
 * @version 版本 版本
 */
public class SKYHelper {

	private static SKYModulesManage mSKYModulesManage = null;

	/**
	 * @return 返回值
	 */
	public static Bind newBind() {
		return new Bind();
	}

	public static class Bind {

		ISKYBind iskyBind;

		/**
		 * @param iskyBind
		 *            参数
		 * @return 返回值
		 */
		public Bind setSkyBind(ISKYBind iskyBind) {
			this.iskyBind = iskyBind;
			return this;
		}

		SKYIViewCommon skyiViewCommon;

		/**
		 * @param skyiViewCommon
		 *            参数
		 * @return 返回值
		 */
		public Bind setIViewCommon(SKYIViewCommon skyiViewCommon) {
			this.skyiViewCommon = skyiViewCommon;
			return this;
		}

		/**
		 * @param application
		 *            参数
		 */
		public void Inject(Application application) {
			if (application == null) {
				throw new RuntimeException("Sky架构:Application没有设置");
			}

			if (this.iskyBind == null) {
				this.iskyBind = ISKYBind.ISKY_BIND;
			}
			if (this.skyiViewCommon == null) {
				this.skyiViewCommon = SKYIViewCommon.SKYI_VIEW_COMMON;
			}

			mSKYModulesManage = iskyBind.getModulesManage();
			if (mSKYModulesManage == null) {
				throw new RuntimeException("Sky架构:SKYModulesManage没有设置");
			}
			DaggerSKYIComponent.builder().sKYModule(new SKYModule(application)).build().inject(mSKYModulesManage);
			mSKYModulesManage.init(iskyBind, skyiViewCommon);
		}

	}

	/**
	 * 获取管理
	 *
	 * @param <M>
	 *            参数
	 * @return 返回值
	 */
	protected static <M> M getManage() {
		return (M) mSKYModulesManage;
	}

	/**
	 * 获取启动管理器
	 *
	 * @param eClass
	 *            参数
	 * @param <D>
	 *            参数
	 * @return 返回值
	 */
	public static <D extends SKYIDisplay> D display(Class<D> eClass) {
		return mSKYModulesManage.getCacheManager().display(eClass);
	}

	/**
	 * 获取业务
	 *
	 * @param service
	 *            参数
	 * @param <B>
	 *            参数
	 * @return 返回值
	 */
	public static final <B extends SKYIBiz> B biz(Class<B> service) {
		return structureHelper().biz(service);
	}

	/**
	 * 获取业务
	 *
	 * @param service
	 *            参数
	 * @param position
	 *            参数
	 * @param <B>
	 *            参数
	 * @return 返回值
	 */
	public static final <B extends SKYIBiz> B biz(Class<B> service, int position) {
		return structureHelper().biz(service, position);
	}

	/**
	 * 业务是否存在
	 *
	 * @param service
	 *            参数
	 * @param <B>
	 *            参数
	 * @return true 存在 false 不存在
	 */
	public static final <B extends SKYIBiz> boolean isExist(Class<B> service) {
		return structureHelper().isExist(service);
	}

	/**
	 * 业务是否存在
	 *
	 * @param service
	 *            参数
	 * @param position
	 *            下标
	 * @param <B>
	 *            参数
	 * @return true 存在 false 不存在
	 */
	public static final <B extends SKYIBiz> boolean isExist(Class<B> service, int position) {
		return structureHelper().isExist(service, position);
	}

	/**
	 * 获取业务
	 *
	 * @param service
	 *            参数
	 * @param <B>
	 *            参数
	 * @return 返回值
	 */
	public static final <B extends SKYIBiz> List<B> bizList(Class<B> service) {
		return structureHelper().bizList(service);
	}

	/**
	 * 公用
	 *
	 * @param service
	 *            参数
	 * @param <B>
	 *            参数
	 * @return 返回值
	 */
	public static final <B extends SKYICommonBiz> B common(Class<B> service) {
		return mSKYModulesManage.getCacheManager().common(service);
	}

	/**
	 * 获取网络
	 *
	 * @param httpClazz
	 *            参数
	 * @param <H>
	 *            参数
	 * @return 返回值
	 */
	public static final <H> H http(Class<H> httpClazz) {
		return mSKYModulesManage.getCacheManager().http(httpClazz);

	}

	/**
	 * 获取实现类
	 *
	 * @param implClazz
	 *            参数
	 * @param <I>
	 *            参数
	 * @return 返回值
	 */
	public static final <I> I interfaces(Class<I> implClazz) {
		return mSKYModulesManage.getCacheManager().interfaces(implClazz);
	}

	/**
	 * 获取方法代理
	 *
	 * @return 返回值
	 */
	public static final SKYMethods methodsProxy() {
		return mSKYModulesManage.getSKYMethods();
	}

	/**
	 * 获取全局上下文
	 *
	 * @return 返回值
	 */
	public static Application getInstance() {
		return mSKYModulesManage.getApplication();
	}

	/**
	 * 获取网络适配器
	 *
	 * @return 返回值
	 */
	public static final Retrofit httpAdapter() {
		return mSKYModulesManage.getSKYRestAdapter();
	}

	/**
	 * 获取网络数据
	 * 
	 * @param call
	 *            参数
	 * @param <D>
	 *            参数
	 * @return 返回值
	 */
	public static final <D> D httpBody(Call<D> call) {
		if (call == null) {
			throw new SKYHttpException("Call 不能为空～");
		}
		Call<D> skyCall;
		if (call.isExecuted()) {
			skyCall = call.clone();
		} else {
			skyCall = call;
		}

		try {
			Response<D> response = skyCall.execute();
			if (!response.isSuccessful()) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("code:");
				stringBuilder.append(response.code());
				stringBuilder.append(" ");
				stringBuilder.append("message:");
				stringBuilder.append(response.message());
				stringBuilder.append(" ");
				stringBuilder.append("errorBody:");
				stringBuilder.append(response.errorBody().string());
				throw new SKYHttpException(stringBuilder.toString());
			}

			return response.body();
		} catch (IOException e) {
			throw new SKYHttpException("网络异常", e.getCause());
		}
	}

	/**
	 * 取消网络请求
	 * 
	 * @param call
	 *            参数
	 */
	public static final void httpCancel(Call call) {
		if (call == null) {
			return;
		}

		if (call.isExecuted()) {
			call.cancel();
		}
	}

	/**
	 * 结构管理器
	 *
	 * @return 管理器
	 */
	public static final SKYStructureIManage structureHelper() {
		return mSKYModulesManage.getSKYStructureManage();
	}

	/**
	 * activity管理器
	 *
	 * @return 管理器
	 */
	public static final SKYScreenManager screenHelper() {
		return mSKYModulesManage.getSKYScreenManager();
	}

	/**
	 * SKYThreadPoolManager 线程池管理器
	 * 
	 * @return 返回值
	 */
	public static final SKYThreadPoolManager threadPoolHelper() {
		return mSKYModulesManage.getSKYThreadPoolManager();
	}

	/**
	 * MainLooper 主线程中执行
	 *
	 * @return 返回值
	 */
	public static final SynchronousExecutor mainLooper() {
		return mSKYModulesManage.getSynchronousExecutor();
	}

	/**
	 * 下载器工具
	 *
	 * @return 返回值
	 */
	public static final SKYDownloadManager downloader() {
		return mSKYModulesManage.getSKYDownloadManager();
	}

	/**
	 * Toast 提示信息
	 *
	 * @return 返回值
	 */
	public static final SKYToast toast() {
		return mSKYModulesManage.getSKYToast();
	}

	/**
	 * 通讯录管理器
	 *
	 * @return 返回值
	 */
	public static final SKYIContact contact() {
		return mSKYModulesManage.getContactManage();
	}

	/**
	 * 判断是否是主线程
	 *
	 * @return true 子线程 false 主线程
	 */
	public static final boolean isMainLooperThread() {
		return Looper.getMainLooper().getThread() != Thread.currentThread();
	}

	/**
	 * 文件缓存管理器
	 *
	 * @return 返回值
	 */
	public static final SKYFileCacheManage fileCacheManage() {
		return mSKYModulesManage.getSKYFileCacheManage();
	}

	/**
	 * 是否打印日志
	 *
	 * @return 返回值
	 */
	public static boolean isLogOpen() {
		return mSKYModulesManage.isLog();
	}

	/**
	 * 公共视图
	 *
	 * @return 返回值
	 */
	public static SKYIViewCommon getComnonView() {
		return mSKYModulesManage.getSkyiViewCommon();
	}
}
