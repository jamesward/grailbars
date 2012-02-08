package com.jamesward.grailbars



import org.junit.*
import grails.test.mixin.*

@TestFor(BarController)
@Mock(Bar)
class BarControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bar/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.barInstanceList.size() == 0
        assert model.barInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.barInstance != null
    }

    void testSave() {
        controller.save()

        assert model.barInstance != null
        assert view == '/bar/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/bar/show/1'
        assert controller.flash.message != null
        assert Bar.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/bar/list'


        populateValidParams(params)
        def bar = new Bar(params)

        assert bar.save() != null

        params.id = bar.id

        def model = controller.show()

        assert model.barInstance == bar
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/bar/list'


        populateValidParams(params)
        def bar = new Bar(params)

        assert bar.save() != null

        params.id = bar.id

        def model = controller.edit()

        assert model.barInstance == bar
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/bar/list'

        response.reset()


        populateValidParams(params)
        def bar = new Bar(params)

        assert bar.save() != null

        // test invalid parameters in update
        params.id = bar.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/bar/edit"
        assert model.barInstance != null

        bar.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/bar/show/$bar.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        bar.clearErrors()

        populateValidParams(params)
        params.id = bar.id
        params.version = -1
        controller.update()

        assert view == "/bar/edit"
        assert model.barInstance != null
        assert model.barInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/bar/list'

        response.reset()

        populateValidParams(params)
        def bar = new Bar(params)

        assert bar.save() != null
        assert Bar.count() == 1

        params.id = bar.id

        controller.delete()

        assert Bar.count() == 0
        assert Bar.get(bar.id) == null
        assert response.redirectedUrl == '/bar/list'
    }
}
