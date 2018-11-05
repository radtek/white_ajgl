package com.taiji.pubsec.ajqlc.sla.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.sla.model.ArchivedFile;
import com.taiji.pubsec.ajqlc.sla.model.DocBianGengJiYaQiXianTongZhiShu;
import com.taiji.pubsec.ajqlc.sla.model.DocChuanHuanNew;
import com.taiji.pubsec.ajqlc.sla.model.DocChuanHuanNewXingShi2012;
import com.taiji.pubsec.ajqlc.sla.model.DocDaiBuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocGongAnXingZhengChuFaNew;
import com.taiji.pubsec.ajqlc.sla.model.DocJianShiJueDingZhiXingTongZhiShuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocJieAnShenPiBiao;
import com.taiji.pubsec.ajqlc.sla.model.DocJieChuJianShiJvZhuJueDingTongZhiShuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocJieChuQuBaoHouShenJueDingShuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocJieChuSheQuJieDuSheQuKangFuTongZhiShuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocJieShouAnJianDengJiBiaoNew;
import com.taiji.pubsec.ajqlc.sla.model.DocJvChuanNew;
import com.taiji.pubsec.ajqlc.sla.model.DocJvLiuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocKouYaJueDingNew;
import com.taiji.pubsec.ajqlc.sla.model.DocKouYaJueDingNew2012;
import com.taiji.pubsec.ajqlc.sla.model.DocLiAnJueDingShuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocPoAnDengJiBiao;
import com.taiji.pubsec.ajqlc.sla.model.DocQiSuYiJianNew;
import com.taiji.pubsec.ajqlc.sla.model.DocQiangZhiGeLiJieDuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocQuBaoHouShenNew;
import com.taiji.pubsec.ajqlc.sla.model.DocShiFangTongZhiNew;
import com.taiji.pubsec.ajqlc.sla.model.DocShiFangTongZhiNew2012;
import com.taiji.pubsec.ajqlc.sla.model.DocTiQingPiZhunDaiBuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocYanChangJvLiuQiXianNew;
import com.taiji.pubsec.ajqlc.sla.model.DocZeLingSheQuJieDuSheQuKangFuJueDingShuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocZhengJvBaoQuanJueDingNew;
import com.taiji.pubsec.ajqlc.sla.model.DocZhengJvBiLuPhoto;
import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 卷宗文书服务接口
 * @author chengkai
 *
 */
public interface IArchivedFileService {
	
	/**
	 * 通过id查询
	 * @param id id
	 * @return 返回卷宗文书信息
	 */
	public ArchivedFile findById(String id);
	
	/**
	 * 通过id查询(行政)传唤证信息
	 * @param docId 文书id
	 * @return (行政)传唤证实体信息
	 */
	public DocChuanHuanNew findDocChuanHuanNewById(String docId);
	
	/**
	 * 通过id查询（刑事）逮捕证信息
	 * @param docId 文书id
	 * @return （刑事）逮捕证实体信息
	 */
	public DocDaiBuNew findDocDaiBuNewById(String docId);
	
	/**
	 * 通过id查询(行政)公安行政处罚决定书信息
	 * @param docId 文书id
	 * @return (行政)公安行政处罚决定书实体信息
	 */
	public DocGongAnXingZhengChuFaNew findDocGongAnXingZhengChuFaNewById(String docId);
	
	/**
	 * 通过id查询（刑事）拘传证信息
	 * @param docId 文书id
	 * @return （刑事）拘传证实体信息
	 */
	public DocJvChuanNew findDocJvChuanNewById(String docId);
	
	/**
	 * 通过id查询（刑事）拘留证信息
	 * @param docId 文书id
	 * @return （刑事）拘留证实体信息
	 */
	public DocJvLiuNew findDocJvLiuNewById(String docId);
	
	/**
	 * 通过id查询查封/扣押决定书信息
	 * @param docId 文书id
	 * @return 查封/扣押决定书实体信息
	 */
	public DocKouYaJueDingNew findDocKouYaJueDingNewById(String docId);
	
	/**
	 * 通过id查询扣押决定书(new_2012)信息
	 * @param docId 文书id
	 * @return 扣押决定书(new_2012)实体信息
	 */
	public DocKouYaJueDingNew2012 findDocKouYaJueDingNew2012ById(String docId);
	
	/**
	 * 通过id查询(行政)强制隔离戒毒决定书信息
	 * @param docId 文书id
	 * @return (行政)强制隔离戒毒决定书实体信息
	 */
	public DocQiangZhiGeLiJieDuNew findDocQiangZhiGeLiJieDuNewById(String docId);
	
	/**
	 * 通过id查询（刑事）起诉意见书（无呈请提请起诉报告书）信息
	 * @param docId 文书id
	 * @return （刑事）起诉意见书（无呈请提请起诉报告书）实体信息
	 */
	public DocQiSuYiJianNew findDocQiSuYiJianNewById(String docId);
	
	/**
	 * 通过id查询（刑事）取保候审决定书/执行通知书信息
	 * @param docId 文书id
	 * @return （刑事）取保候审决定书/执行通知书实体信息
	 */
	public DocQuBaoHouShenNew findDocQuBaoHouShenNewById(String docId);
	
	/**
	 * 通过id查询（刑事）释放通知书信息
	 * @param docId 文书id
	 * @return （刑事）释放通知书实体信息
	 */
	public DocShiFangTongZhiNew findDocShiFangTongZhiNewById(String docId);
	
	/**
	 * 通过id查询（刑事）释放通知书信息
	 * @param docId 文书id
	 * @return （刑事）释放通知书实体信息
	 */
	public DocShiFangTongZhiNew2012 findDocShiFangTongZhiNew2012ById(String docId);
	
	/**
	 * 通过id查询（刑事）提请批准逮捕书信息
	 * @param docId 文书id
	 * @return （刑事）提请批准逮捕书实体信息
	 */
	public DocTiQingPiZhunDaiBuNew findDocTiQingPiZhunDaiBuNewById(String docId);
	
	/**
	 * 通过id查询（刑事）延长拘留期限通知书信息
	 * @param docId 文书id
	 * @return （刑事）延长拘留期限通知书实体信息
	 */
	public DocYanChangJvLiuQiXianNew findDocYanChangJvLiuQiXianNewById(String docId);
	
	/**
	 * 通过id查询证据保全决定书（无证据保全清单）信息
	 * @param docId 文书id
	 * @return 证据保全决定书（无证据保全清单）实体信息
	 */
	public DocZhengJvBaoQuanJueDingNew findDocZhengJvBaoQuanJueDingNewById(String docId);
	
	/**
	 * 通过id查询监视居住决定执行通知书信息
	 * @param docId 文书id
	 * @return 监视居住决定执行通知书实体信息
	 */
	public DocJianShiJueDingZhiXingTongZhiShuNew findDocJianShiJueDingZhiXingTongZhiShuNewById(String docId);
	
	/**
	 * 通过id查询解除监视居住决定通知书信息
	 * @param docId 文书id
	 * @return 解除监视居住决定通知书实体信息
	 */
	public DocJieChuJianShiJvZhuJueDingTongZhiShuNew findDocJieChuJianShiJvZhuJueDingTongZhiShuNewById(String docId);
	
	/**
	 * 通过id查询破案登记表信息
	 * @param docId 文书id
	 * @return 破案登记表实体信息
	 */
	public DocPoAnDengJiBiao findDocPoAnDengJiBiaoById(String docId);
	
	/**
	 * 通过id查询责令社区戒毒 社区康复决定书信息
	 * @param docId 文书id
	 * @return 责令社区戒毒 社区康复决定书实体信息
	 */
	public DocZeLingSheQuJieDuSheQuKangFuJueDingShuNew findDocZeLingSheQuJieDuSheQuKangFuJueDingShuNewById(String docId);
	
	/**
	 * 通过id查询解除社区戒毒 社区康复通知书信息
	 * @param docId 文书id
	 * @return 解除社区戒毒 社区康复通知书实体信息
	 */
	public DocJieChuSheQuJieDuSheQuKangFuTongZhiShuNew findDocJieChuSheQuJieDuSheQuKangFuTongZhiShuNewById(String docId);
	
	/**
	 * 通过id查询(刑事)传唤证信息
	 * @param docId 文书id
	 * @return (刑事)传唤证实体信息
	 */
	public DocChuanHuanNewXingShi2012 findDocChuanHuanNewXingShi2012ById(String docId);
	
	/**
	 * 通过id查询接受案件登记表信息
	 * @param docId 文书id
	 * @return 接受案件登记表实体信息
	 */
	public DocJieShouAnJianDengJiBiaoNew findDocJieShouAnJianDengJiBiaoNewById(String docId);
	
	/**
	 * 通过id查询立案决定书信息
	 * @param docId 文书id
	 * @return 立案决定书实体信息
	 */
	public DocLiAnJueDingShuNew findDocLiAnJueDingShuNewById(String docId);
	
	/**
	 * 通过id查询(行政)结案审批表信息
	 * @param docId 文书id
	 * @return (行政)结案审批表实体信息
	 */
	public DocJieAnShenPiBiao findDocJieAnShenPiBiaoById(String docId);
	
	/**
	 * 通过案件id查询案件文书关系信息列表
	 * @param caseId 案件id
	 * @return 返回案件文书关心信息列表
	 */
	List<ArchivedFile> findArchivedFileByCase(String caseId);
	
	/**
	 * 通过案件id和文书类型查询文书关系信息列表
	 * @param caseId 案件id
	 * @param type 文书类型
	 * @return 返回案件文书关系信息列表
	 */
	List<ArchivedFile> findArchivedFileByCaseIdAndType(String caseId, String type);
	
	/**
	 * 通过文书id和文书类型查询对应案件的法制审核人
	 * @param docId 文书id
	 * @param docType 文书类型
	 * @return 人员对象
	 */
	Person findPersonByDocIdAndDocType(String docId, String docType);
	
	/**
	 * 通过案件id和嫌疑人id查询“延长拘留期限通知书”
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回“延长拘留期限通知书”信息
	 */
	public List<DocYanChangJvLiuQiXianNew> findDocYanChangJvLiuQiXianNewByCaseIdAndSuspectId(String caseId, String SuspectId);
	
	/**
	 * 通过案件id和嫌疑人id查询”提请批准逮捕书“
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回”提请批准逮捕书“信息
	 */
	public List<DocTiQingPiZhunDaiBuNew> findDocTiQingPiZhunDaiBuNewByCaseIdAndSuspectId(String caseId, String SuspectId);
	
	/**
	 * 通过案件id和嫌疑人id查询”取保候审决定/执行通知书“
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回”取保候审决定/执行通知书“信息
	 */
	public List<DocQuBaoHouShenNew> findDocQuBaoHouShenNewByCaseIdAndSuspectId(String caseId, String SuspectId);
	
	/**
	 * 通过案件id和嫌疑人id查询”移送起诉“
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回”移送起诉“信息
	 */
	public List<DocQiSuYiJianNew> findDocQiSuYiJianNewByCaseIdAndSuspectId(String caseId, String SuspectId);
	
	/**
	 * 通过案件id和嫌疑人id查询”监视居住决定执行通知书“
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回”监视居住决定执行通知书“信息
	 */
	public List<DocJianShiJueDingZhiXingTongZhiShuNew> findDocJianShiJueDingZhiXingTongZhiShuNewByCaseIdAndSuspectId(String caseId, String SuspectId);
	
	/**
	 * 通过案件id和嫌疑人id查询”解除监视居住决定通知书“
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回”解除监视居住决定通知书“信息
	 */
	public List<DocJieChuJianShiJvZhuJueDingTongZhiShuNew> findDocJieChuJianShiJvZhuJueDingTongZhiShuNewByCaseIdAndSuspectId(String caseId, String SuspectId);
	
	/**
	 * 通过案件id和嫌疑人id查询”(行政)结案审批表“
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回”(行政)结案审批表“信息
	 */
	public List<DocJieAnShenPiBiao> findDocJieAnShenPiBiaoNewByCaseIdAndSuspectId(String caseId, String SuspectId);
	
	/**
	 * 通过案件id和嫌疑人id查询”(行政)公安行政处罚决定书“
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回”(行政)公安行政处罚决定书“信息
	 */
	public List<DocGongAnXingZhengChuFaNew> findDocGongAnXingZhengChuFaNewByCaseIdAndSuspectId(String caseId, String SuspectId);
	
	/**
	 * 通过id查询解除取保候审决定书/通知书信息
	 * @param docId 文书id
	 * @return 解除取保候审决定书/通知书实体信息
	 */
	public DocJieChuQuBaoHouShenJueDingShuNew findDocJieChuQuBaoHouShenJueDingShuNewById(String docId);
	
	/**
	 * 通过案件id和嫌疑人id查询”解除取保候审决定书/通知书“
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回”解除取保候审决定书/通知书“信息
	 */
	public List<DocJieChuQuBaoHouShenJueDingShuNew> findDocJieChuQuBaoHouShenJueDingShuNewByCaseIdAndSuspectId(String caseId, String SuspectId);

	/**
	 * 通过id查询变更羁押期限通知书信息
	 * @param docId 文书id
	 * @return 变更羁押期限通知书实体信息
	 */
	public DocBianGengJiYaQiXianTongZhiShu findDocBianGengJiYaQiXianTongZhiShuById(String docId);
	
	/**
	 * 通过案件id和嫌疑人id查询”变更羁押期限通知书“
	 * @param caseId 案件id
	 * @param SuspectId 嫌疑人id
	 * @return 返回”变更羁押期限通知书“信息
	 */
	public List<DocBianGengJiYaQiXianTongZhiShu> findDocBianGengJiYaQiXianTongZhiShuByCaseIdAndSuspectId(String caseId, String SuspectId);

	/**
	 * 通过案件id查询证据笔录信息list
	 * @param caseId 案件id
	 * @return 返回案件笔录list
	 */
	List<DocZhengJvBiLuPhoto> findDocZhengJvBiLuPhotosByCase(String caseId);
	
	/**
	 * 通过证据笔录id查询证据笔录 
	 * @param photoId 证据笔录id
	 * @return 返回证据笔录
	 */
	public DocZhengJvBiLuPhoto findDocZhengJvBiLuPhotoByPhotoId(String photoId);
}
