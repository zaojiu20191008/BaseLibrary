package com.niubility.library.mvp;


/**
 * 基础契约类， 各模块按照此模板编写，mvp各层 继承 BaseXXX
 */
public class BaseContract {

    public interface IModel extends BaseModel {

    }

    public interface IView extends BaseView {

    }

    public abstract class Presenter extends BasePresenter<IModel, IView> {

    }

}
