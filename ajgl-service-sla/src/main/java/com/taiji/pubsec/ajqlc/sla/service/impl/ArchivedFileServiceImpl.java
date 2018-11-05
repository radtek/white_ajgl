package com.taiji.pubsec.ajqlc.sla.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.sla.model.ArchivedFile;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
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
import com.taiji.pubsec.ajqlc.sla.service.IArchivedFileService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;

@Service("archivedFileService")
public class ArchivedFileServiceImpl implements IArchivedFileService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public ArchivedFile findById(String id) {
		return (ArchivedFile) this.dao.findById(ArchivedFile.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocChuanHuanNew findDocChuanHuanNewById(String docId) {
		return (DocChuanHuanNew) this.dao.findById(DocChuanHuanNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocDaiBuNew findDocDaiBuNewById(String docId) {
		return (DocDaiBuNew) this.dao.findById(DocDaiBuNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocGongAnXingZhengChuFaNew findDocGongAnXingZhengChuFaNewById(String docId) {
		return (DocGongAnXingZhengChuFaNew) this.dao.findById(DocGongAnXingZhengChuFaNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocJvChuanNew findDocJvChuanNewById(String docId) {
		return (DocJvChuanNew) this.dao.findById(DocJvChuanNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocJvLiuNew findDocJvLiuNewById(String docId) {
		return (DocJvLiuNew) this.dao.findById(DocJvLiuNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocKouYaJueDingNew findDocKouYaJueDingNewById(String docId) {
		return (DocKouYaJueDingNew) this.dao.findById(DocKouYaJueDingNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocKouYaJueDingNew2012 findDocKouYaJueDingNew2012ById(String docId) {
		return (DocKouYaJueDingNew2012) this.dao.findById(DocKouYaJueDingNew2012.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocQiangZhiGeLiJieDuNew findDocQiangZhiGeLiJieDuNewById(String docId) {
		return (DocQiangZhiGeLiJieDuNew) this.dao.findById(DocQiangZhiGeLiJieDuNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocQiSuYiJianNew findDocQiSuYiJianNewById(String docId) {
		return (DocQiSuYiJianNew) this.dao.findById(DocQiSuYiJianNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocQuBaoHouShenNew findDocQuBaoHouShenNewById(String docId) {
		return (DocQuBaoHouShenNew) this.dao.findById(DocQuBaoHouShenNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocShiFangTongZhiNew findDocShiFangTongZhiNewById(String docId) {
		return (DocShiFangTongZhiNew) this.dao.findById(DocShiFangTongZhiNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocShiFangTongZhiNew2012 findDocShiFangTongZhiNew2012ById(String docId) {
		return (DocShiFangTongZhiNew2012) this.dao.findById(DocShiFangTongZhiNew2012.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocTiQingPiZhunDaiBuNew findDocTiQingPiZhunDaiBuNewById(String docId) {
		return (DocTiQingPiZhunDaiBuNew) this.dao.findById(DocTiQingPiZhunDaiBuNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocYanChangJvLiuQiXianNew findDocYanChangJvLiuQiXianNewById(String docId) {
		return (DocYanChangJvLiuQiXianNew) this.dao.findById(DocYanChangJvLiuQiXianNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocZhengJvBaoQuanJueDingNew findDocZhengJvBaoQuanJueDingNewById(String docId) {
		return (DocZhengJvBaoQuanJueDingNew) this.dao.findById(DocZhengJvBaoQuanJueDingNew.class, docId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DocJianShiJueDingZhiXingTongZhiShuNew findDocJianShiJueDingZhiXingTongZhiShuNewById(String docId) {
		return (DocJianShiJueDingZhiXingTongZhiShuNew) this.dao.findById(DocJianShiJueDingZhiXingTongZhiShuNew.class, docId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivedFile> findArchivedFileByCase(String caseId) {
		String xql = "select af from ArchivedFile as af where af.criminalBasicCase.caseCode = ?";
		return this.dao.findAllByParams(ArchivedFile.class, xql, new Object[]{caseId}) == null ? new ArrayList<ArchivedFile>() : this.dao.findAllByParams(ArchivedFile.class, xql, new Object[]{caseId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArchivedFile> findArchivedFileByCaseIdAndType(String caseId, String type) {
		String xql = "select af from ArchivedFile as af where af.criminalBasicCase.caseCode = ? and af.type = ?";
		return this.dao.findAllByParams(ArchivedFile.class, xql, new Object[]{caseId, type});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Person findPersonByDocIdAndDocType(String docId, String docType) {
		String xql = "select af from ArchivedFile as af where af.sourceId = ? and af.type = ?";
		ArchivedFile archivedFile = (ArchivedFile) this.dao.findByParams(ArchivedFile.class, xql, new Object[]{docId, docType});
		if(archivedFile == null){
			return null;
		}else{
			CriminalBasicCase criminalBasicCase = archivedFile.getCriminalBasicCase();
			if(criminalBasicCase == null){
				return null;
			}else{
				if(criminalBasicCase.getCaseAttachedInfo() == null){
					return null;
				}else{
					String personId = criminalBasicCase.getCaseAttachedInfo().getDoPerson();
					if(personId == null){
						return null;
					}else{
						return (Person) this.dao.findById(Person.class, personId);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocYanChangJvLiuQiXianNew> findDocYanChangJvLiuQiXianNewByCaseIdAndSuspectId(String caseId,
			String SuspectId) {
		String xql = "select d from DocYanChangJvLiuQiXianNew as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocYanChangJvLiuQiXianNew.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocTiQingPiZhunDaiBuNew> findDocTiQingPiZhunDaiBuNewByCaseIdAndSuspectId(String caseId, String SuspectId) {
		String xql = "select d from DocTiQingPiZhunDaiBuNew as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocTiQingPiZhunDaiBuNew.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocQuBaoHouShenNew> findDocQuBaoHouShenNewByCaseIdAndSuspectId(String caseId, String SuspectId) {
		String xql = "select d from DocQuBaoHouShenNew as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocQuBaoHouShenNew.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocQiSuYiJianNew> findDocQiSuYiJianNewByCaseIdAndSuspectId(String caseId, String SuspectId) {
		String xql = "select d from DocQiSuYiJianNew as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocQiSuYiJianNew.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocJieChuJianShiJvZhuJueDingTongZhiShuNew findDocJieChuJianShiJvZhuJueDingTongZhiShuNewById(String docId) {
		return (DocJieChuJianShiJvZhuJueDingTongZhiShuNew) this.dao.findById(DocJieChuJianShiJvZhuJueDingTongZhiShuNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocPoAnDengJiBiao findDocPoAnDengJiBiaoById(String docId) {
		return (DocPoAnDengJiBiao) this.dao.findById(DocPoAnDengJiBiao.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocZeLingSheQuJieDuSheQuKangFuJueDingShuNew findDocZeLingSheQuJieDuSheQuKangFuJueDingShuNewById(
			String docId) {
		return (DocZeLingSheQuJieDuSheQuKangFuJueDingShuNew) this.dao.findById(DocZeLingSheQuJieDuSheQuKangFuJueDingShuNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocJieChuSheQuJieDuSheQuKangFuTongZhiShuNew findDocJieChuSheQuJieDuSheQuKangFuTongZhiShuNewById(
			String docId) {
		return (DocJieChuSheQuJieDuSheQuKangFuTongZhiShuNew) this.dao.findById(DocJieChuSheQuJieDuSheQuKangFuTongZhiShuNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocJieShouAnJianDengJiBiaoNew findDocJieShouAnJianDengJiBiaoNewById(String docId) {
		return (DocJieShouAnJianDengJiBiaoNew) this.dao.findById(DocJieShouAnJianDengJiBiaoNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocLiAnJueDingShuNew findDocLiAnJueDingShuNewById(String docId) {
		return (DocLiAnJueDingShuNew) this.dao.findById(DocLiAnJueDingShuNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocChuanHuanNewXingShi2012 findDocChuanHuanNewXingShi2012ById(String docId) {
		return (DocChuanHuanNewXingShi2012) this.dao.findById(DocChuanHuanNewXingShi2012.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocJianShiJueDingZhiXingTongZhiShuNew> findDocJianShiJueDingZhiXingTongZhiShuNewByCaseIdAndSuspectId(
			String caseId, String SuspectId) {
		String xql = "select d from DocJianShiJueDingZhiXingTongZhiShuNew as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocJianShiJueDingZhiXingTongZhiShuNew.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocJieChuJianShiJvZhuJueDingTongZhiShuNew> findDocJieChuJianShiJvZhuJueDingTongZhiShuNewByCaseIdAndSuspectId(
			String caseId, String SuspectId) {
		String xql = "select d from DocJieChuJianShiJvZhuJueDingTongZhiShuNew as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocJieChuJianShiJvZhuJueDingTongZhiShuNew.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocJieAnShenPiBiao findDocJieAnShenPiBiaoById(String docId) {
		return (DocJieAnShenPiBiao) this.dao.findById(DocJieAnShenPiBiao.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocJieAnShenPiBiao> findDocJieAnShenPiBiaoNewByCaseIdAndSuspectId(String caseId, String SuspectId) {
		String xql = "select d from DocJieAnShenPiBiao as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocJieAnShenPiBiao.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocGongAnXingZhengChuFaNew> findDocGongAnXingZhengChuFaNewByCaseIdAndSuspectId(String caseId,
			String SuspectId) {
		String xql = "select d from DocGongAnXingZhengChuFaNew as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocGongAnXingZhengChuFaNew.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocJieChuQuBaoHouShenJueDingShuNew findDocJieChuQuBaoHouShenJueDingShuNewById(String docId) {
		return (DocJieChuQuBaoHouShenJueDingShuNew) this.dao.findById(DocJieChuQuBaoHouShenJueDingShuNew.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocJieChuQuBaoHouShenJueDingShuNew> findDocJieChuQuBaoHouShenJueDingShuNewByCaseIdAndSuspectId(String caseId,
			String SuspectId) {
		String xql = "select d from DocJieChuQuBaoHouShenJueDingShuNew as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocJieChuQuBaoHouShenJueDingShuNew.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocBianGengJiYaQiXianTongZhiShu findDocBianGengJiYaQiXianTongZhiShuById(String docId) {
		return (DocBianGengJiYaQiXianTongZhiShu) this.dao.findById(DocBianGengJiYaQiXianTongZhiShu.class, docId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocBianGengJiYaQiXianTongZhiShu> findDocBianGengJiYaQiXianTongZhiShuByCaseIdAndSuspectId(String caseId,
			String SuspectId) {
		String xql = "select d from DocBianGengJiYaQiXianTongZhiShu as d where d.caseid = ? and d.personid = ?";
		return this.dao.findAllByParams(DocBianGengJiYaQiXianTongZhiShu.class, xql, new Object[]{caseId, SuspectId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocZhengJvBiLuPhoto> findDocZhengJvBiLuPhotosByCase(String caseId) {
		String xql = "select d from DocZhengJvBiLuPhoto as d where d.caseid = ? order by d.sort";
		return this.dao.findAllByParams(DocZhengJvBiLuPhoto.class, xql, new Object[]{caseId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocZhengJvBiLuPhoto findDocZhengJvBiLuPhotoByPhotoId(String photoId) {
		return (DocZhengJvBiLuPhoto) dao.findById(DocZhengJvBiLuPhoto.class, photoId);
	}
}
