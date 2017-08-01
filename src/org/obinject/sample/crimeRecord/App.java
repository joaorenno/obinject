/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.crimeRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.device.File;
import org.obinject.meta.Uuid;
import org.obinject.storage.BTree;
import org.obinject.storage.BTreeEntity;
import org.obinject.storage.MTree;
import org.obinject.storage.RTree;

/**
 *
 * @author Mariana
 */
public class App {

    private static final int OBI_FILE_PAGE_SIZE = 4096;
//    private static final int CRIMERECORD_INSTANCES = 148910;
    private static final int CRIMERECORD_INSTANCES = 10000;

    public static void main(String[] args) throws IOException {
        BufferedReader txtFile = null;
        try {
            txtFile = new BufferedReader(
                    new InputStreamReader(new FileInputStream("resources/Crimes2014_ObiND.asc")));
        } catch (FileNotFoundException ex) {
            System.out.println("Could not open input file.");
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        //ObInjectFile
        File xObIFile = new File("CrimeRecord.obi", OBI_FILE_PAGE_SIZE);

        // BTReeEntty Structure
        BTreeEntity<EntityCrimeRecord> pxEntityBTree = new BTreeEntity<EntityCrimeRecord>(xObIFile) {
        };
        // BTree Structure
        BTree<OrderCrimeRecord> pxKeyBTree = new BTree<OrderCrimeRecord>(xObIFile) {
        };
        // MTree Structure
        MTree<PointCrimeRecord> pxKeyMTree = new MTree<PointCrimeRecord>(xObIFile) {
        };
        // RTree Structure
        RTree<RectangleCrimeRecord> pxKeyRTree = new RTree<RectangleCrimeRecord>(xObIFile) {
        };

        CrimeRecord xCrimeRecord = new CrimeRecord();

        // Auxiliary variables
        String sStr; // String to read

        for (int i = 0; i < CRIMERECORD_INSTANCES; i++) {
            if (i % 1000 == 0) {
                // See things progressing...
                System.out.println(i + ",");
            }

            // Get entire line from input file.
            sStr = txtFile.readLine();

            // Create tokenizer to get fields.
            StringTokenizer tok = new StringTokenizer(sStr, "\t");

            // Read object from file
            // ID
            xCrimeRecord.setID(Integer.parseInt(tok.nextToken()));
            // Case Number
            xCrimeRecord.setCaseNumber(tok.nextToken());
            // Block
            xCrimeRecord.setBlock(tok.nextToken());
            // IUCR
            xCrimeRecord.setIUCR(tok.nextToken());
            // Location Descriptor
            xCrimeRecord.setLocationDescription((short) CrimeRecord.GetLocationDescriptionIndex(tok.nextToken()));
            // Arrest
            xCrimeRecord.setArrest(tok.nextToken().equalsIgnoreCase("true"));
            // Community Area
            xCrimeRecord.setCommunityArea((byte) Integer.parseInt(tok.nextToken()));
            // X and Y Coordinates
            xCrimeRecord.setXCoord(Long.parseLong(tok.nextToken()));
            xCrimeRecord.setYCoord(Long.parseLong(tok.nextToken()));
            // Latitude and Longitude
            xCrimeRecord.setLatitude(Double.parseDouble(tok.nextToken()));
            xCrimeRecord.setLongitude(Double.parseDouble(tok.nextToken()));
            // Read Uuid
            Uuid pxUuid = Uuid.fromString(tok.nextToken());

            // Create entity
            EntityCrimeRecord pxCrimeRecordEntity = new EntityCrimeRecord(xCrimeRecord, pxUuid);

            //Create BTree Key
            OrderCrimeRecord pxCrimeRecordOrder = new OrderCrimeRecord(pxCrimeRecordEntity);
            // Create MTree key
            PointCrimeRecord pxCrimeRecordPoint = new PointCrimeRecord(pxCrimeRecordEntity);
            //Create RTree key
            RectangleCrimeRecord pxCrimeRecordRectangle = new RectangleCrimeRecord(pxCrimeRecordEntity);

            // Add to trees
            // BTreeEntity
            pxEntityBTree.add(pxCrimeRecordEntity);
            // BTree
            pxKeyBTree.add(pxCrimeRecordOrder);
            // MTree
            pxKeyMTree.add(pxCrimeRecordPoint);
            // RTree
            pxKeyRTree.add(pxCrimeRecordRectangle);

            // Search trees
            // Search Entity Tree
            EntityCrimeRecord pxCrimeRecordEntityFound = pxEntityBTree.find(pxUuid);
            // Verify if entity is ok.
            if (pxCrimeRecordEntityFound == null) {
                // Found anything?
                System.out.println("[BTREEENTITY] Entity not found: " + pxUuid.toString());
            } else {
                if (pxCrimeRecordEntityFound.getUuid().compareTo(pxUuid) != 0) {
                    // Is it the right Uuid?
                    System.out.println("[BTREEENTITY] Mismatched UUIDs: searched for UUID "
                            + pxUuid.toString() + " and found UUID "
                            + pxCrimeRecordEntityFound.getUuid().toString());
                } else {
                    if (!(pxCrimeRecordEntityFound.isEqual(pxCrimeRecordEntity))) {
                        // Right UUID but diferent values - Data inconsistency
                        System.out.println("[BTREEENTITY] Data Inconsistency in UUID "
                                + pxUuid.toString());
                    } else {
                        //cout + "[BTREEENTITY] Entity OK!" + endl;
                    }
                }
            }

            // Seach BTree
            Uuid pxUuidFound = pxKeyBTree.find(pxCrimeRecordOrder);
            // Found anything?
            if (pxUuidFound == null) {
                System.out.println("[BTREE] Key not found: "
                        + pxCrimeRecordOrder.getUuid().toString());
            } else {
                if (pxCrimeRecordOrder.getUuid().compareTo(pxUuidFound) != 0) {
                    // Is it the right Uuid?
                    System.out.println("[BTREE] Mismatched UUIDs: searched for UUID "
                            + pxCrimeRecordOrder.getUuid().toString() + " and found UUID "
                            + pxUuidFound.toString());
                } else {
                    //cout + "[BTREE] Key OK!" + endl;
                }
            }

            // Seach MTree
            pxUuidFound = pxKeyMTree.find(pxCrimeRecordPoint);
            // Found anything?
            if (pxUuidFound == null) {
                System.out.println("MTREE] Key not found: "
                        + pxCrimeRecordPoint.getUuid().toString());
            } else {
                if (pxCrimeRecordPoint.getUuid().compareTo(pxUuidFound) != 0) {
                    // Is it the right Uuid?
                    System.out.println("[MTREE] Mismatched UUIDs: searched for UUID "
                            + pxCrimeRecordPoint.getUuid().toString()
                            + " and found UUID " + pxUuidFound.toString());
                } else {
                    //cout + "[MTREE] Key OK!" + endl;
                }
            }
            // Seach RTree
            pxUuidFound = pxKeyRTree.find(pxCrimeRecordRectangle);
            // Found anything?
            if (pxUuidFound == null) {
                System.out.println("[RTREE] Key not found: "
                        + pxCrimeRecordRectangle.getUuid().toString());
            } else {
                if (pxCrimeRecordRectangle.getUuid().compareTo(pxUuidFound) != 0) {
                    // Is it the right Uuid?
                    System.out.println("[RTREE] Mismatched UUIDs: searched for UUID "
                            + pxCrimeRecordRectangle.getUuid().toString()
                            + " and found UUID " + pxUuidFound.toString());
                } else {
                    //cout + "[RTREE] Key OK!" + endl;
                }
            }
        }

        System.out.println("Results.");
//        System.out.println("[BTREEENTITY] Height: " + pxEntityBTree.height());
        System.out.println("[BTREEENTITY - PERSISTANCE] Comparisons: "
                + pxEntityBTree.getAverageForAdd().getTotalVerifications());
        System.out.println("[BTREEENTITY - PERSISTANCE] Disk accesses: "
                + pxEntityBTree.getAverageForAdd().getTotalDiskAcess());
        System.out.println("[BTREEENTITY - PERSISTANCE] Time (ms): "
                + pxEntityBTree.getAverageForAdd().getTotalTime());
        System.out.println("[BTREEENTITY - RECOVERY] Comparisons: "
                + pxEntityBTree.getAverageForFind().getTotalVerifications());
        System.out.println("[BTREEENTITY - RECOVERY] Disk accesses: "
                + pxEntityBTree.getAverageForFind().getTotalDiskAcess());
        System.out.println("[BTREEENTITY - RECOVERY] Time (ms): "
                + pxEntityBTree.getAverageForFind().getTotalTime());
        System.out.println("");
//        System.out.println("[BTREE] Height: " + pxKeyBTree.height());
        System.out.println("[BTREE - PERSISTANCE] Comparisons: "
                + pxKeyBTree.getAverageForAdd().getTotalVerifications());
        System.out.println("[BTREE - PERSISTANCE] Disk accesses: "
                + pxKeyBTree.getAverageForAdd().getTotalDiskAcess());
        System.out.println("[BTREE - PERSISTANCE] Time (ms): "
                + pxKeyBTree.getAverageForAdd().getTotalTime());
        System.out.println("[BTREE - RECOVERY] Comparisons: "
                + pxKeyBTree.getAverageForFind().getTotalVerifications());
        System.out.println("[BTREE - RECOVERY] Disk accesses: "
                + pxKeyBTree.getAverageForFind().getTotalDiskAcess());
        System.out.println("[BTREE - RECOVERY] Time (ms): "
                + pxKeyBTree.getAverageForFind().getTotalTime());
        System.out.println("");

        //        System.out.println("[MTREE] Height: " + pxKeyMTree.height());
        System.out.println("[MTREE - PERSISTANCE] Comparisons: "
                + pxKeyMTree.getAverageForAdd().getTotalVerifications());
        System.out.println("[MTREE - PERSISTANCE] Disk accesses: "
                + pxKeyMTree.getAverageForAdd().getTotalDiskAcess());
        System.out.println("[MTREE - PERSISTANCE] Time (ms): "
                + pxKeyMTree.getAverageForAdd().getTotalTime());
        System.out.println("[MTREE - RECOVERY] Comparisons: "
                + pxKeyMTree.getAverageForFind().getTotalVerifications());
        System.out.println("[MTREE - RECOVERY] Disk accesses: "
                + pxKeyMTree.getAverageForFind().getTotalDiskAcess());
        System.out.println("[MTREE - RECOVERY] Time (ms): "
                + pxKeyMTree.getAverageForFind().getTotalTime());
        System.out.println("");

        //        System.out.println("[RTREE] Height: " + pxKeyRTree.height());
        System.out.println("[RTREE - PERSISTANCE] Comparisons: "
                + pxKeyRTree.getAverageForAdd().getTotalVerifications());
        System.out.println("[RTREE - PERSISTANCE] Disk accesses: "
                + pxKeyRTree.getAverageForAdd().getTotalDiskAcess());
        System.out.println("[RTREE - PERSISTANCE] Time (ms): "
                + pxKeyRTree.getAverageForAdd().getTotalTime());
        System.out.println("[RTREE - RECOVERY] Comparisons: "
                + pxKeyRTree.getAverageForFind().getTotalVerifications());
        System.out.println("[RTREE - RECOVERY] Disk accesses: "
                + pxKeyRTree.getAverageForFind().getTotalDiskAcess());
        System.out.println("[RTREE - RECOVERY] Time (ms): "
                + pxKeyRTree.getAverageForFind().getTotalTime());
        System.out.println("");
        txtFile.close();

        System.out.println("End of execution.");

    }

}
