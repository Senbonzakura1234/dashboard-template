package com.app.manager.controller;

import com.app.manager.entity.File;
import com.app.manager.model.HelperMethod;
import com.app.manager.model.midware_model.ModelFile;
import com.app.manager.service.interfaceClass.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class DashboardController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    FileService fileService;

    @GetMapping({"/dashboard", "/"})
    public String index(Model model) {
        Pageable pageableIndexNumber = PageRequest.of(0, 5, Sort.by("indexNumber").ascending());
        Pageable pageableUpdatedAt = PageRequest.of(0, 5, Sort.by("updatedat").descending());

        Page<ModelFile> pendingFiles = fileService.getAll("",
                File.StatusEnum.PENDING, pageableIndexNumber);

        Page<ModelFile> downloadingFiles = fileService.getAll("",
                File.StatusEnum.DOWNLOADING, pageableUpdatedAt);

        Page<ModelFile> downloadedFiles = fileService.getAll("",
                File.StatusEnum.DOWNLOADED, pageableUpdatedAt);

        Page<ModelFile> unzippingFiles = fileService.getAll("",
                File.StatusEnum.UNZIPPING, pageableUpdatedAt);

        Page<ModelFile> unzippedFiles = fileService.getAll("",
                File.StatusEnum.UNZIPPED, pageableUpdatedAt);

        Page<ModelFile> uploadingFiles = fileService.getAll("",
                File.StatusEnum.UPLOADING, pageableUpdatedAt);

        Page<ModelFile> doneFiles = fileService.getAll("",
                File.StatusEnum.DONE, pageableUpdatedAt);

        model.addAttribute("pendingParam", File.StatusEnum.PENDING.toString());
        model.addAttribute("downloadingParam", File.StatusEnum.DOWNLOADING.toString());
        model.addAttribute("unzippingParam", File.StatusEnum.UNZIPPING.toString());
        model.addAttribute("uploadingParam", File.StatusEnum.UPLOADING.toString());

        model.addAttribute("pendingFiles", pendingFiles);
        model.addAttribute("downloadingFiles", downloadingFiles);
        model.addAttribute("unzippingFiles", unzippingFiles);
        model.addAttribute("uploadingFiles", uploadingFiles);

        var pendingCount = pendingFiles.getTotalElements();
        var downloadingCount = downloadingFiles.getTotalElements();
        var downloadedCount = downloadedFiles.getTotalElements();
        var unzippingCount = unzippingFiles.getTotalElements();
        var unzippedCount = unzippedFiles.getTotalElements();
        var uploadingCount = uploadingFiles.getTotalElements();
        var doneCount = doneFiles.getTotalElements();
        var totalCount = doneCount + uploadingCount + unzippedCount +
            unzippingCount + downloadedCount + downloadingCount + pendingCount;


        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("downloadingCount", downloadingCount);
        model.addAttribute("downloadedCount", downloadedCount);
        model.addAttribute("unzippingCount", unzippingCount);
        model.addAttribute("unzippedCount", unzippedCount);
        model.addAttribute("uploadingCount", uploadingCount);
        model.addAttribute("doneCount", doneCount);
        model.addAttribute("totalCount", totalCount);

        var pendingPercent = HelperMethod.roundUpIntDiv(pendingCount*100, totalCount);
        var downloadingPercent = HelperMethod.roundUpIntDiv(downloadingCount*100, totalCount);
        var downloadedPercent = HelperMethod.roundUpIntDiv(downloadedCount*100, totalCount);
        var unzippingPercent = HelperMethod.roundUpIntDiv(unzippingCount*100, totalCount);
        var unzippedPercent = HelperMethod.roundUpIntDiv(unzippedCount*100, totalCount);
        var uploadingPercent = HelperMethod.roundUpIntDiv(uploadingCount*100, totalCount);
        var donePercent = HelperMethod.roundUpIntDiv(doneCount*100, totalCount);


        long[] percentList = {pendingPercent, downloadingPercent, downloadedPercent,
                unzippingPercent, unzippedPercent, uploadingPercent, donePercent};
        long max = Arrays.stream(percentList).max().getAsLong();
        long replace = 100;
        for (long item: percentList) {
            if(item != max) replace -= item;
        }

        model.addAttribute("pendingPercent", pendingPercent != max? pendingPercent : replace);
        model.addAttribute("downloadingPercent", downloadingPercent != max? downloadingPercent : replace);
        model.addAttribute("downloadedPercent", downloadedPercent != max? downloadedPercent : replace);
        model.addAttribute("unzippingPercent", unzippingPercent != max? unzippingPercent : replace);
        model.addAttribute("unzippedPercent", unzippedPercent != max? unzippedPercent : replace);
        model.addAttribute("uploadingPercent", uploadingPercent != max? uploadingPercent : replace);
        model.addAttribute("donePercent", donePercent != max? donePercent : replace);

        HelperMethod.pingTo("https://senbonzakura-ping-app.herokuapp.com");

        return "views/dashboard";
    }
}
