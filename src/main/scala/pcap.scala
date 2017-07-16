package com.scalawilliam.scalanative

import scala.scalanative.native
import scala.scalanative.native._
import scala.scalanative.runtime.struct

/**
  * @see https://linux.die.net/man/3/pcap
  */
@native.link("wpcap") // Note: if using WinPcap on windows, link against "wpcap", otherwise use "pcap"
@native.extern
object pcap {

  /** This is just a pointer for us, we don't care what is inside **/
  type pcap_handle = native.Ptr[Unit]

  type pcap_pkthdr = native.CStruct4[native.CUnsignedLong,
                                     native.CUnsignedLong,
                                     native.CUnsignedInt,
                                     native.CUnsignedInt]

  def pcap_open_live(deviceName: CString,
                     snapLen: CInt,
                     promisc: CInt,
                     to_ms: CInt,
                     errbuf: CString): pcap_handle =
    native.extern

  def pcap_open_offline(fname: CString, errbuf: CString): pcap_handle =
    native.extern

  def pcap_next(p: native.Ptr[Unit],
                h: native.Ptr[pcap_pkthdr]): native.CString = native.extern

  def pcap_close(p: native.Ptr[Unit]): Unit = native.extern

  @struct class pcap_if {
    var next: native.Ptr[pcap_if] = _
    var name: native.Ptr[native.CChar] = _
    var description: native.Ptr[native.CChar] = _
    var addresses: native.Ptr[Unit] = _ // This points to a struct that we don't care about.
    var flags: native.CUnsignedInt = _
  }

  def pcap_findalldevs(devs_out: Ptr[Ptr[pcap_if]],
                       errbuf: CString): native.CInt = native.extern

  def pcap_freealldevs(devices: native.Ptr[pcap_if]): Unit = native.extern
}
