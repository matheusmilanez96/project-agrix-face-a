package com.betrybe.agrix.services;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FarmRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FarmService.
 */
@Service
public class FarmService {

  private final FarmRepository farmRepository;

  private final CropRepository cropRepository;

  @Autowired
  public FarmService(FarmRepository farmRepository, CropRepository cropRepository) {
    this.farmRepository = farmRepository;
    this.cropRepository = cropRepository;
  }

  public Farm insertFarm(Farm farm) {
    return farmRepository.save(farm);
  }

  public Optional<Farm> getFarmById(Long id) {
    return farmRepository.findById(id);
  }


  public List<Farm> getAllFarms() {
    return farmRepository.findAll();
  }

  /**
   * Método insertCrop.
   */
  public Crop insertCrop(Crop crop) {
    return cropRepository.save(crop);
  }

  public List<Crop> getCropsById(Farm farm) {
    List<Crop> allCrops = farm.getCrops();
    String name = farm.getName();
    Long farmId = farm.getId();
//    List<Crop> crops = cropRepository.findByFarmId(farmId);
    return allCrops;
  }
}
