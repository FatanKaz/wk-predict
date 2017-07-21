package com.ahmadarif.wekaclassifier.service

import com.ahmadarif.wekaclassifier.extension.loader
import org.springframework.stereotype.Service
import weka.classifiers.Evaluation
import weka.classifiers.bayes.NaiveBayesUpdateable
import weka.classifiers.functions.SMO
import weka.classifiers.trees.J48
import weka.core.Instance
import weka.core.Instances
import weka.core.Utils
import weka.core.converters.ArffLoader
import java.io.File
import java.util.*


/**
 * Created by ARIF on 20-Jul-17.
 */
@Service
class WekaService {

    fun sample1(file: File, className: String): String {
        // load data
        val loader = ArffLoader()
        loader.setFile(file)
        val data = loader.dataSet
        val classAttr = data.attribute(className)

        // set class
        data.setClassIndex(classAttr.index())

        val options = arrayOf("-U")
        val model = J48()
        model.options = options
        model.buildClassifier(data)

        return model.toString()
    }

    fun sample2(file: File, className: String): String {
        // load data
        val loader = loader(file)
        val data = loader.dataSet

        // set class
        data.setClassIndex(data.numAttributes() - 1)

        val options = arrayOf("-U")
        val model = J48()
        model.options = options
        model.buildClassifier(data)

        return model.toString()
    }

    fun sample3(file: File, className: String): String {
        // load data
        val loader = loader(file)
        val data = loader.dataSet
        val classAttr = data.attribute(className)

        // set class
        data.setClassIndex(classAttr.index())

        // train
        val model = J48()
        model.options = arrayOf("-U")
        model.buildClassifier(data)

        // test
        val eval = Evaluation(data)
        eval.crossValidateModel(model, data, 10, Random(1))
        eval.evaluateModel(model, data)

        println(eval.toClassDetailsString())

        return eval.toSummaryString("\nResults\n======\n", false)
    }

    fun sample4(file: File, className: String): String {
        // load data
        val loader = loader(file)
        val data:Instances = loader.dataSet
        val classAttr = data.attribute(className)

        // set class
        data.setClassIndex(classAttr.index())

        // train NaiveBayes
        val model = NaiveBayesUpdateable()
        model.buildClassifier(data)

        var current: Instance?
        do {
            current = loader.getNextInstance(data) ?: break
            model.updateClassifier(current)
        } while (true)

        // output generated model
        println(model)
        return model.toString()
    }

    fun sample5(file: File, className: String): String {
        val message = StringBuffer()
        val startTime = System.currentTimeMillis()

        // load data
        val loader = loader(file)
        val data = loader.dataSet
        val classAttr = data.attribute(className)

        // set class
        data.setClassIndex(classAttr.index())

//        message.appendln("===== Data Summary =====")
//        message.appendln(data.toSummaryString())


        // build a J48 decision tree
//        val model = J48()
//        model.buildClassifier(data)

        //SVM
        val model = SMO()

        val optionStr = "-C 1.0 -L 0.001 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01\""
        model.options = Utils.splitOptions(optionStr)
        model.buildClassifier(data)

//        message.appendln()
//        message.appendln("===== Data Summary =====")
//        message.appendln(model.toString())


        // prediction
        message.appendln()
        message.appendln("===== Prediction Result =====")

        val eval = Evaluation(data)
        eval.crossValidateModel(model, data, 5, Random(1))
        message.appendln(eval.toSummaryString())
//        for (i in 0..data.numInstances()-1) {
//            val clsLabel = model.classifyInstance(data[i]).toInt()
//            message.appendln("Prediction ke-${i+1} = ${data.classAttribute().value(clsLabel)}")
//        }

        message.appendln("Time = ${System.currentTimeMillis() - startTime}")
        return message.toString()
    }
}