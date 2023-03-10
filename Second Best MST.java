/*

Author - Shamura Ahmad

*/
package com.mycompany.secondbestmst;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.File;
import java.io.IOException;

class SecondBestMST {

    class Edge implements Comparable<Edge> {

        int src, dest, weight;

        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }

    int V, E;
    Edge edge[];
    Edge result[];
    Edge result1[];
    Edge result2[];
    int leader[];

    SecondBestMST(int v, int e) {
        V = v;
        E = e;
        leader = new int[V + 1];
        edge = new Edge[E];

        for (int i = 0; i < e; ++i) {
            edge[i] = new Edge();
        }
    }

    void MakeSet() {
        for (int i = 0; i <= V; i++) {
            leader[i] = i;
        }
    }

    int FindSet(int n) {
        return leader[n];
    }

    void Union(int u, int v) {
        int newLeader = Math.min(u, v);
        int preLeader = Math.max(u, v);
        for (int i = 0; i <= V; i++) {
            if (leader[i] == preLeader) {
                leader[i] = newLeader;
            }
        }
    }

    Edge[] Print(Edge MST[]) {
        for (int i = 0; i < MST.length; i++) {
            System.out.println(MST[i].src + " --> " + MST[i].dest + " == " + MST[i].weight);
        }
        return MST;
    }

    int Cost(Edge MST[]) {
        int cost = 0;
        for (int i = 0; i < MST.length; i++) {
            cost += MST[i].weight;
        }
        return cost;
    }

    Edge[] CreateEdge() {
        Edge MST[] = new Edge[V - 1];
        for (int i = 0; i < MST.length; i++) {
            MST[i] = new Edge();
        }
        return MST;
    }

    void KruskalMST() {
        result = CreateEdge();
        Arrays.sort(edge);
        MakeSet();
        int i = 0;
        for (Edge next_edge : edge) {
            int x = FindSet(next_edge.src);
            int y = FindSet(next_edge.dest);
            if (x != y) {
                result[i++] = next_edge;
                Union(x, y);
            }
        }

        System.out.println("Minimum Spanning Tree Edges :");
        Print(result);
        System.out.println("Minimum Spanning Tree Cost : " + Cost(result));
    }

    void SecondBestMST() {
        result1 = CreateEdge();
        result2 = CreateEdge();
        int sCost = Integer.MAX_VALUE;
        int cost;
        for (int k = 0; k < V - 1; k++) {
            MakeSet();
            int i = 0;
            for (Edge next_edge : edge) {
                if (next_edge != result[k]) {
                    int x = FindSet(next_edge.src);
                    int y = FindSet(next_edge.dest);
                    if (x != y) {
                        result1[i++] = next_edge;
                        Union(x, y);
                    }
                }
            }
            cost = Cost(result1);
            if (cost < sCost) {
                sCost = cost;
                result2 = result1;
            }
        }

        System.out.println("\nSecond Best Minimum Spanning Tree Edges :");
        Print(result2);
        System.out.println("Second Best Minimum Spanning Tree Cost : " + sCost);
    }

    public static void main(String[] args) throws FileNotFoundException {
        int V;
        int E;
        File file = new File("C:\\Users\\great computer\\Desktop\\SecondBestMST.txt");
        Scanner sc = new Scanner(file);
        V = sc.nextInt();
        E = sc.nextInt();
        SecondBestMST graph = new SecondBestMST(V, E);
        for (int i = 0; i < E; i++) {
            graph.edge[i].src = sc.nextInt();
            graph.edge[i].dest = sc.nextInt();
            graph.edge[i].weight = sc.nextInt();
        }
        graph.KruskalMST();
        graph.SecondBestMST();
    }
}
